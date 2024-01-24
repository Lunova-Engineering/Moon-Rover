package com.lunova.network.http.message.common;

/**
 * Enum representing major web browsers and their respective user-agent strings.
 * A user-agent string is a string sent by the browser to the server to identify
 * the browser type, version, and the operating system it's running on.
 */
public enum UserAgent {
    /**
     * Chrome: Google's web browser.
     * Example User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36.
     */
    CHROME("Chrome", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"),

    /**
     * Firefox: Mozilla's web browser.
     * Example User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0.
     */
    FIREFOX("Firefox", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0"),

    /**
     * Edge: Microsoft's web browser.
     * Example User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.59.
     */
    EDGE("Edge", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.59"),

    /**
     * Safari: Apple's web browser.
     * Example User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15.
     */
    SAFARI("Safari", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.2 Safari/605.1.15"),

    /**
     * Opera: A web browser developed by Opera Software.
     * Example User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36 OPR/63.0.3368.107.
     */
    OPERA("Opera", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36 OPR/63.0.3368.107"),

    /**
     * Internet Explorer: A series of graphical web browsers developed by Microsoft.
     * Example User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko.
     */
    INTERNET_EXPLORER("Internet Explorer", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
    ;

    private final String name;
    private final String userAgent;

    /**
     * Constructor for Browser.
     *
     * @param name        the name of the browser.
     * @param userAgent   the user-agent string associated with the browser.
     */
    UserAgent(String name, String userAgent) {
        this.name = name;
        this.userAgent = userAgent;
    }

    /**
     * Retrieves the name of the browser.
     *
     * @return the name of the browser.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the user-agent string associated with the browser.
     *
     * @return the user-agent string.
     */
    public String getUserAgent() {
        return userAgent;
    }
}
