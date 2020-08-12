package br.dev.kurtis.clientapp;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Optional;

@Configuration
public class RetrofitConfig {

    @Bean
    public OkHttpClient httpClient(final Tracer tracer) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    final Request request = chain.request();
                    final var context = Optional.of(tracer)
                            .map(Tracer::currentSpan)
                            .map(Span::context)
                            .map(tracer::newChild)
                            .map(Span::context);
                    final Request.Builder builder = request.newBuilder();
                    context.map(TraceContext::traceIdString).ifPresent(traceId -> builder.addHeader("X-B3-TraceId", traceId));
                    context.map(TraceContext::parentIdString).ifPresent(parentId -> builder.addHeader("X-B3-ParentSpanId", parentId));
                    context.map(TraceContext::spanIdString).ifPresent(spanId -> builder.addHeader("X-B3-SpanId", spanId));
                    context.map(TraceContext::sampled).map(Object::toString).ifPresent(sampled -> builder.addHeader("X-B3-Sampled", sampled));
                    return chain.proceed(builder.build());
                })
                .build();
    }

    @Bean
    public GsonConverterFactory converterFactory() {
        return GsonConverterFactory.create();
    }

    @Bean
    public Retrofit retrofit(final OkHttpClient client, final GsonConverterFactory converter) {
        return new Retrofit.Builder()
//                .baseUrl("http://localhost:8082/")
                .baseUrl("http://observability-server-app:8080/")
                .client(client)
                .addConverterFactory(converter)
                .build();
    }

    @Bean
    public MatchClient MatchClient(final Retrofit retrofit) {
        return retrofit.create(MatchClient.class);
    }
}
