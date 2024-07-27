package com.pgwaktupagi.userservice.constant;


public class ProductDocumentation {

    private ProductDocumentation() {
    }

    public static final String GET_ALL_DATA_DOC = "{\n" +
            "  \"statusCode\": \"200\",\n" +
            "  \"message\": \"Sukses get all data\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": \"669886f57dbcca5c5b13c8fe\",\n" +
            "      \"name\": \"Milo minuman\",\n" +
            "      \"price\": 2000,\n" +
            "      \"stock\": 2000,\n" +
            "      \"description\": \"Milo minuman enak harga 2000\",\n" +
            "      \"category\": \"Makanan\",\n" +
            "      \"image\": \"ai-generated-8673811_640.png\",\n" +
            "      \"imageUrl\": \"http://localhost:8000/api/image/1721272053953_ai-generated-8673811_640.png\",\n" +
            "      \"createdAt\": \"2024-07-18T10:07:33.977\",\n" +
            "      \"updatedAt\": \"2024-07-18T10:12:56.569\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"669887d07dbcca5c5b13c8ff\",\n" +
            "      \"name\": \"ciki\",\n" +
            "      \"price\": 100,\n" +
            "      \"stock\": 50,\n" +
            "      \"description\": \"Milo minuman enak\",\n" +
            "      \"category\": \"minuman\",\n" +
            "      \"image\": \"\",\n" +
            "      \"imageUrl\": \"http://localhost:8000/api/image/1721272272217_\",\n" +
            "      \"createdAt\": \"2024-07-18T10:11:12.217\",\n" +
            "      \"updatedAt\": \"2024-07-18T10:11:12.217\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"6698b1fcf11d377a0a466d6d\",\n" +
            "      \"name\": \"thai tea rasa melon\",\n" +
            "      \"price\": 2000,\n" +
            "      \"stock\": 2000,\n" +
            "      \"description\": \"Milo minuman enak harga 2000\",\n" +
            "      \"category\": \"minuman\",\n" +
            "      \"image\": \"Capture.png\",\n" +
            "      \"imageUrl\": \"http://localhost:8000/api/image/1721284195398_Capture.png\",\n" +
            "      \"createdAt\": \"2024-07-18T13:11:08.347\",\n" +
            "      \"updatedAt\": \"2024-07-18T13:29:55.403\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static final String FETCH_PRODUCT_DOC = "{\n" +
            "  \"statusCode\": \"200\",\n" +
            "  \"message\": \"Success fetch data\",\n" +
            "  \"data\": {"+
            "       \"id\": \"669886f57dbcca5c5b13c8fe\",\n" +
            "       \"name\": \"milo minuman\",\n" +
            "       \"price\": 2000,\n" +
            "       \"stock\": 2000,\n" +
            "       \"description\": \"Milo minuman enak harga 2000\",\n" +
            "       \"category\": \"makanan\",\n" +
            "       \"image\": \"ai-generated-8673811_640.png\",\n" +
            "       \"imageUrl\": \"http://localhost:8000/api/image/1721272053953_ai-generated-8673811_640.png\",\n" +
            "       \"createdAt\": \"2024-07-18T10:07:33.977\",\n" +
            "       \"updatedAt\": \"2024-07-18T10:12:56.569\"\n" +
            "    }\n" +
            "}";

    public static final String CREATE_PRODUCT_DOC = "{\n" +
            "  \"statusCode\": \"201\",\n" +
            "  \"message\": \"Success create data\",\n" +
            "  \"data\": {"+
            "       \"id\": \"669886f57dbcca5c5b13c8fe\",\n" +
            "       \"name\": \"milo minuman\",\n" +
            "       \"price\": 2000,\n" +
            "       \"stock\": 2000,\n" +
            "       \"description\": \"Milo minuman enak harga 2000\",\n" +
            "       \"category\": \"makanan\",\n" +
            "       \"image\": \"ai-generated-8673811_640.png\",\n" +
            "       \"imageUrl\": \"http://localhost:8000/api/image/1721272053953_ai-generated-8673811_640.png\",\n" +
            "       \"createdAt\": \"2024-07-18T10:07:33.977\",\n" +
            "       \"updatedAt\": \"2024-07-18T10:12:56.569\"\n" +
            "    }\n" +
            "}";

    public static final String UPDATE_PRODUCT_DOC = "{\n" +
            "  \"statusCode\": \"200\",\n" +
            "  \"message\": \"Success update data\",\n" +
            "  \"data\": {"+
            "       \"id\": \"669886f57dbcca5c5b13c8fe\",\n" +
            "       \"name\": \"milo minuman\",\n" +
            "       \"price\": 2000,\n" +
            "       \"stock\": 2000,\n" +
            "       \"description\": \"Milo minuman enak harga 2000\",\n" +
            "       \"category\": \"makanan\",\n" +
            "       \"image\": \"ai-generated-8673811_640.png\",\n" +
            "       \"imageUrl\": \"http://localhost:8000/api/image/1721272053953_ai-generated-8673811_640.png\",\n" +
            "       \"createdAt\": \"2024-07-18T10:07:33.977\",\n" +
            "       \"updatedAt\": \"2024-07-18T10:12:56.569\"\n" +
            "    }\n" +
            "}";

    public static final String DELETE_PRODUCT_DOC = "{\n" +
            "  \"statusCode\": \"200\",\n" +
            "  \"message\": \"Success delete data\",\n" +
            "  \"data\": {"+
            "    }\n" +
            "}";

    public static final String ERROR_DATA_DOC = "{" +
            "\"apiPath\": \"/api/path\"," +
            " \"errorCode\": \"NOT_FOUND\"," +
            " \"errorMessage\": \"Resource not found\"," +
            " \"errorTime\": \"2024-07-18T15:30:00\"}";
}
