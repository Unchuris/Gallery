package unchuris.vladislav.gallery.api;

/**
 * Interface for link generation.
 */
public interface IApiGenerationLink {
    /**
     * Get public link.
     * @param publicKey link to a public folder.
     * @param queryParameters string with query parameters.
     * @return full link.
     */
    String getPublicLink(String publicKey, String queryParameters);

    /**
     * Get query parameters.
     * @param size specific image size.
     * @param limit download limit.
     * @param sort sort field.
     * @return queryParameters
     */
    String getQueryParameters(String size, Integer limit, String sort);
}
