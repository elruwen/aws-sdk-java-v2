/*
 * Copyright 2010-2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.extensions.dynamodb.mappingclient.core;

import java.util.Iterator;
import java.util.function.Function;

import software.amazon.awssdk.annotations.SdkInternalApi;

@SdkInternalApi
public class TransformIterable<T, R> implements Iterable<R> {
    private final Iterable<T> wrappedIterable;
    private final Function<T, R> transformFunction;

    private TransformIterable(Iterable<T> wrappedIterable, Function<T, R> transformFunction) {
        this.wrappedIterable = wrappedIterable;
        this.transformFunction = transformFunction;
    }

    public static <T, R> TransformIterable<T, R> of(Iterable<T> iterable, Function<T, R> transformFunction) {
        return new TransformIterable<>(iterable, transformFunction);
    }

    @Override
    public Iterator<R> iterator() {
        return TransformIterator.of(wrappedIterable.iterator(), transformFunction);
    }
}
