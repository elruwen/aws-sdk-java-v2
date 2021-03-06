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

package software.amazon.awssdk.extensions.dynamodb.mappingclient;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import software.amazon.awssdk.extensions.dynamodb.mappingclient.functionaltests.models.FakeItem;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@RunWith(MockitoJUnitRunner.class)
public class TableOperationTest {

    private static final String FAKE_RESULT = "fake-result";
    private static final String FAKE_TABLE_NAME = "fake-table-name";

    private final FakeTableOperation fakeTableOperation = new FakeTableOperation();

    @Mock
    private MapperExtension mockMapperExtension;

    @Mock
    private DynamoDbClient mockDynamoDbClient;

    @Test
    public void executeOnPrimaryIndex_defaultImplementation_callsExecuteCorrectly() {
        fakeTableOperation.executeOnPrimaryIndex(FakeItem.getTableSchema(),
                                                 FAKE_TABLE_NAME,
                                                 mockMapperExtension,
                                                 mockDynamoDbClient);

        assertThat(fakeTableOperation.lastDynamoDbClient, sameInstance(mockDynamoDbClient));
        assertThat(fakeTableOperation.lastMapperExtension, sameInstance(mockMapperExtension));
        assertThat(fakeTableOperation.lastTableSchema, sameInstance(FakeItem.getTableSchema()));
        assertThat(fakeTableOperation.lastOperationContext, is(
            OperationContext.of(FAKE_TABLE_NAME, TableMetadata.primaryIndexName())));
    }

    private static class FakeTableOperation implements TableOperation<FakeItem, String, String, String> {
        private TableSchema<FakeItem> lastTableSchema = null;
        private OperationContext lastOperationContext = null;
        private MapperExtension lastMapperExtension = null;
        private DynamoDbClient lastDynamoDbClient = null;

        @Override
        public String generateRequest(TableSchema<FakeItem> tableSchema, OperationContext context,
                                      MapperExtension mapperExtension) {
            return null;
        }

        @Override
        public Function<String, String> serviceCall(DynamoDbClient dynamoDbClient) {
            return null;
        }

        @Override
        public String transformResponse(String response, TableSchema<FakeItem> tableSchema, OperationContext context,
                                        MapperExtension mapperExtension) {
            return null;
        }

        @Override
        public String execute(TableSchema<FakeItem> tableSchema,
                              OperationContext context,
                              MapperExtension mapperExtension,
                              DynamoDbClient dynamoDbClient) {
            lastTableSchema = tableSchema;
            lastOperationContext = context;
            lastMapperExtension = mapperExtension;
            lastDynamoDbClient = dynamoDbClient;
            return FAKE_RESULT;
        }
    }
}