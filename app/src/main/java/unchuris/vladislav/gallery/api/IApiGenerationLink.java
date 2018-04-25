package unchuris.vladislav.gallery.api;

/**
 * Interface for link generation.
 */
public interface IApiGenerationLink {
    /**
     *
     * @param publicKey link to a public folder.
     * @param queryParameters string with query parameters.
     * @return full link.
     */
    String getPublicLink(String publicKey, String queryParameters);
}
