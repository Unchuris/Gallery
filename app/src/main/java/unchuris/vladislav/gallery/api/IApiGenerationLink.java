package unchuris.vladislav.gallery.api;

/**
 * Interface for link generation.
 */
public interface IApiGenerationLink {

    String getPublicLink(String publicKey, String queryParameters);

    String getQueryParameters(String size, Integer limit, String sort);
}
