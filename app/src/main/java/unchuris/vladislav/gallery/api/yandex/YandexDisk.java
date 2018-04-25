package unchuris.vladislav.gallery.api.yandex;

import unchuris.vladislav.gallery.api.IApiGenerationLink;

/**
 * Class to work with Yandex disk API.
 */
public class YandexDisk implements IApiGenerationLink {
    /**
     * Host for all API requests: cloud-api.yandex.net.
     * API version: v1.
     * Path: disk/public/resources.
     */
    private static final String PUBLIC_RESOURCE_URL = "cloud-api.yandex.net/v1/disk/public/resources";

    /**
     *
     * @param publicKey link to a public folder.
     * @param queryParameters string with query parameters.
     * @return full link to the public Yandex folder using api.
     */
    public String getPublicLink(final String publicKey, final String queryParameters) {
        return "https://" + PUBLIC_RESOURCE_URL + "?" +
                "public_key=" + publicKey + "&" + queryParameters;
    }
}
