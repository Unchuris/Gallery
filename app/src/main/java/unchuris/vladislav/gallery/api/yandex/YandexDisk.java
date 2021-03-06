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


    public String getPublicLink(final String publicKey, final String queryParameters) {
        return "https://" + PUBLIC_RESOURCE_URL + "?" +
                "public_key=" + publicKey + "&" + queryParameters;
    }

    public String getQueryParameters(final String size,
                                  final Integer limit,
                                  final String sort) {
        return "preview_size=" + size +
                "&preview_crop=true" +
                "&limit=" + limit +
                "&sort=" + sort;
    }
}
