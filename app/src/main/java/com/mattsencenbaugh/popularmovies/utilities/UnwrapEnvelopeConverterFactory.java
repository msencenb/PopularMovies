package com.mattsencenbaugh.popularmovies.utilities;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by msencenb on 12/13/17.
 */

/*private class UnwrapEnvelopeConverterFactory extends Converter.Factory {
    UnwrapEnvelopeConverterFactory() {}

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return null;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Type envelopeType = Type.newParameterizedType(Envelope.class, type);

        final Converter<ResponseBody, Envelope> delegateConverter =
                retrofit.nextResponseBodyConverter(this, envelopeType, annotations);

        return new UnwrapEnvelopeConverterFactory(delegateConverter);
    }
}
*/