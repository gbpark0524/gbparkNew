package ke.pe.gbpark.response;

/**
 * @param success A boolean indicating whether the request was successful.
 * @param message A message providing additional information about the request outcome.
 * @param data The data returned by the request.
 * @param <T> The type of the data contained in the response.
 */
public record Response<T>(boolean success, String message, T data) {
    public static final String SUCCESS_MESSAGE = "Request succeeded";

    public static final String EMPTY_MESSAGE = "No results found";
}