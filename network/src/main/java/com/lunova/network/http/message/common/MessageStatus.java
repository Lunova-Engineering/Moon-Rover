package com.lunova.network.http.message.common;

/**
 * Enum representing all HTTP status codes.
 * HTTP status codes are issued by a server in response to a client's request made to the server.
 * They are divided into five classes: informational responses, successful responses,
 * redirects, client errors, and server errors.
 */
public enum MessageStatus {
        // 1xx Informational Responses

        /**
         * 100 Continue: Use when the initial part of a request has been received.
         * Example: During a POST request, the client starts by sending the headers and waits
         * for the server to respond with a 100 Continue status before sending the body.
         */
        _100_CONTINUE(100, "Continue", "Indicates that the initial part of a request has been received and has not yet been rejected by the server."),

        /**
         * 101 Switching Protocols: Use when the server switches protocols as requested by the client.
         * Example: A client requests an upgrade to a WebSocket connection, and the server responds with 101 Switching Protocols.
         */
        _101_SWITCHING_PROTOCOLS(101, "Switching Protocols", "Indicates that the server is switching protocols according to the request."),

        /**
         * 102 Processing: Use when the request is being processed, but no response is available yet.
         * Example: Commonly used in WebDAV systems where the request may take a while to process and the server provides this code to indicate ongoing processing.
         */
        _102_PROCESSING(102, "Processing", "Indicates that the server has received and is processing the request, but no response is available yet."),

        /**
         * 103 Early Hints: Use to return preliminary headers before the full HTTP response.
         * Example: A server sends resource hints, allowing the client to preload resources while the server prepares the full response.
         */
        _103_EARLY_HINTS(103, "Early Hints", "Used to return some response headers before final HTTP message."),

        // ... Other 1xx status codes

        // 2xx Successful Responses

        /**
         * 200 OK: The standard response for successful requests.
         * Example: After a successful GET request, the server sends a 200 OK status with the requested data.
         */
        _200_OK(200, "OK", "Indicates that the request has succeeded."),

    /**
     * 201 Created: Use when a new resource has been successfully created.
     * Example: After a POST request that results in creating a new resource, the server responds with a 201 Created status.
     */
    _201_CREATED(201, "Created", "Indicates that the request has been fulfilled and resulted in a new resource being created."),

    /**
     * 202 Accepted: Use when the request has been accepted but processing is not complete.
     * Example: The server accepts a request for processing, but the processing has not been completed.
     */
    _202_ACCEPTED(202, "Accepted", "Indicates that the request has been accepted for processing, but the processing has not been completed."),

    /**
     * 203 Non-Authoritative Information: Use when the returned meta-information is from a copy or a secondary source.
     * Example: The server successfully processed the request, but is returning information from another source.
     */
    _203_NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information", "Indicates that the request was successful but the enclosed payload has been modified."),

    /**
     * 204 No Content: Use when no content is returned in the response.
     * Example: A successful request that doesn't require sending back any body content, like a DELETE request.
     */
    _204_NO_CONTENT(204, "No Content", "Indicates that there is no content to send for this request, but the headers may be useful."),

    /**
     * 205 Reset Content: Use to instruct the client to reset the document view.
     * Example: A form is successfully submitted and the client is instructed to clear the form.
     */
    _205_RESET_CONTENT(205, "Reset Content", "Indicates that the client should reset the document view that caused the request."),

    /**
     * 206 Partial Content: Use when the server successfully processes a partial GET request.
     * Example: The client makes a range request to download a portion of a large file.
     */
    _206_PARTIAL_CONTENT(206, "Partial Content", "Indicates that the server is delivering only part of the resource due to a range header."),

    /**
     * 207 Multi-Status: Use in WebDAV for multiple independent operations.
     * Example: Multiple resources are affected, and the response provides status for each one.
     */
    _207_MULTI_STATUS(207, "Multi-Status", "Provides status for multiple independent operations."),

    /**
     * 208 Already Reported: Use in WebDAV, to avoid listing resources more than once.
     * Example: Part of a DAV binding's members are listed earlier in the multistatus response.
     */
    _208_ALREADY_REPORTED(208, "Already Reported", "Indicates that the members of a DAV binding have already been enumerated in a previous part of the multistatus response, and are not being included again."),

    /**
     * 226 IM Used: Use when the server has fulfilled a GET request for the resource, and the response represents the result of one or more instance-manipulations applied to the current instance.
     * Example: The server responds to a GET request for a resource which has been altered by applied instance manipulations.
     */
    _226_IM_USED(226, "IM Used", "Indicates that the server has fulfilled a GET request for the resource, and the response is a representation of the result of one or more instance-manipulations applied to the current instance."),

        // 3xx Redirection Messages
    /**
     * 300 Multiple Choices: Use when multiple options for the resource are available.
     * Example: The server provides different formats of a resource, and the client can choose one.
     */
    _300_MULTIPLE_CHOICES(300, "Multiple Choices", "Indicates multiple options for the resource that the client may follow."),

    /**
     * 301 Moved Permanently: Use when the resource has been permanently moved to a new location.
     * Example: The client requests a page, but it has been permanently moved to a new URL, which is provided in the response.
     */
    _301_MOVED_PERMANENTLY(301, "Moved Permanently", "This response code means that the URI of requested resource has been changed permanently."),

    /**
     * 302 Found: Use for a temporary redirection.
     * Example: The client requests a resource, but it is temporarily located at a different URI, as provided by the server.
     */
    _302_FOUND(302, "Found", "Indicates that the resource requested has been temporarily moved to a different URI."),

    /**
     * 303 See Other: Use when the client should make a GET request to another URI.
     * Example: After a POST operation, the server provides a URI for the client to retrieve the resulting resource.
     */
    _303_SEE_OTHER(303, "See Other", "Indicates that the server sent this response to direct the client to get the requested resource at another URI with a GET request."),

    /**
     * 304 Not Modified: Use when the resource has not been modified since the last request.
     * Example: The client requests a resource with a conditional GET, and the resource has not changed since the last access.
     */
    _304_NOT_MODIFIED(304, "Not Modified", "Indicates that there is no need to retransmit the requested resources as it has not been modified since the last request."),

    /**
     * 305 Use Proxy: Use to indicate that the requested resource must be accessed through a proxy.
     * Example: The client requests a resource, but it must be accessed through a proxy, which is provided in the response.
     * @deprecated This response code is no longer used; it was deprecated due to security concerns regarding in-band configuration of a proxy.
     */
    _305_USE_PROXY(305, "Use Proxy", "Deprecated. Indicates that the requested resource is available only through a proxy, the address for which is provided in the response."),

    /**
     * 307 Temporary Redirect: Use for a temporary redirection without changing the request method.
     * Example: The client sends a POST request, and the server temporarily redirects to another URI without changing the request method.
     */
    _307_TEMPORARY_REDIRECT(307, "Temporary Redirect", "Indicates that the resource requested has been temporarily moved to the URL given by the Location headers."),

    /**
     * 308 Permanent Redirect: Use for a permanent redirection without changing the request method.
     * Example: The client sends a POST request, and the server permanently redirects to another URI without changing the request method.
     */
    _308_PERMANENT_REDIRECT(308, "Permanent Redirect", "Indicates that the resource is permanently moved to a new URL provided by the Location headers."),

        // 4xx Client Error Responses
    /**
     * 400 Bad Request: Use when the server cannot process the request due to a client error.
     * Example: The client sends a request with malformed syntax, invalid request message framing, or deceptive request routing.
     */
    _400_BAD_REQUEST(400, "Bad Request", "Indicates that the server cannot or will not process the request due to an apparent client error."),

    /**
     * 401 Unauthorized: Use when authentication is required and has failed or has not been provided.
     * Example: The request lacks valid authentication credentials for the requested resource.
     */
    _401_UNAUTHORIZED(401, "Unauthorized", "Indicates that the request has not been applied because it lacks valid authentication credentials."),

    /**
     * 402 Payment Required: Reserved for future use.
     * Example: Currently unused, it's intended for use in digital payment systems.
     */
    _402_PAYMENT_REQUIRED(402, "Payment Required", "Reserved for future use."),

    /**
     * 403 Forbidden: Use when the server understands the request but refuses to authorize it.
     * Example: The client does not have access rights to the content; the server is refusing to give the requested resource.
     */
    _403_FORBIDDEN(403, "Forbidden", "Indicates that the server understood the request but refuses to authorize it."),

    /**
     * 404 Not Found: Use when the server cannot find the requested resource.
     * Example: The client requests a resource that is not available or does not exist.
     */
    _404_NOT_FOUND(404, "Not Found", "Indicates that the server cannot find the requested resource."),

    /**
     * 405 Method Not Allowed: Use when the request method is known by the server but not supported by the target resource.
     * Example: The client sends a POST request to a resource that only supports GET requests.
     */
    _405_METHOD_NOT_ALLOWED(405, "Method Not Allowed", "Indicates that the request method is known by the server but is not supported by the target resource."),

    /**
     * 406 Not Acceptable: Use when the server cannot produce a response matching the list of acceptable values.
     * Example: The client requests a resource but requires a specific content type that the server cannot provide.
     */
    _406_NOT_ACCEPTABLE(406, "Not Acceptable", "Indicates that the server cannot produce a response matching the list of acceptable values defined in the request's proactive content negotiation headers."),

    /**
     * 407 Proxy Authentication Required: Use when the client must first authenticate itself with the proxy.
     * Example: The client request must be authenticated with the proxy server before it is sent to the final destination.
     */
    _407_PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required", "Indicates that the client must first authenticate itself with the proxy."),

    /**
     * 408 Request Timeout: Use when the server times out waiting for the request.
     * Example: The client fails to send a request within the time that the server was prepared to wait.
     */
    _408_REQUEST_TIMEOUT(408, "Request Timeout", "Indicates that the server timed out waiting for the request."),

    /**
     * 409 Conflict: Use when the request could not be processed because of conflict in the current state of the resource.
     * Example: The request conflicts with the current state of the server, such as a request to delete a non-empty directory.
     */
    _409_CONFLICT(409, "Conflict", "Indicates that the request could not be processed because of conflict in the current state of the resource."),

    /**
     * 410 Gone: Use when the resource requested is no longer available and will not be available again.
     * Example: The client requests a resource that used to exist but has been permanently removed.
     */
    _410_GONE(410, "Gone", "Indicates that the resource requested is no longer available and will not be available again."),

    /**
     * 411 Length Required: Use when the server refuses to accept the request without a defined Content-Length.
     * Example: The client sends a request without a Content-Length header, and the server requires it.
     */
    _411_LENGTH_REQUIRED(411, "Length Required", "Indicates that the server refuses to accept the request without a defined Content-Length."),

    /**
     * 412 Precondition Failed: Use when the server does not meet one of the preconditions set by the client.
     * Example: The client sends conditional requests like "If-Match" but one of the conditions fails.
     */
    _412_PRECONDITION_FAILED(412, "Precondition Failed", "Indicates that the server does not meet one of the preconditions that the requester put on the request."),

    /**
     * 413 Payload Too Large: Use when the request entity is larger than limits defined by the server.
     * Example: The client sends a request with a body size exceeding the maximum limit set by the server.
     */
    _413_PAYLOAD_TOO_LARGE(413, "Payload Too Large", "Indicates that the request entity is larger than limits defined by server."),

    /**
     * 414 URI Too Long: Use when the URI requested by the client is longer than the server is willing to interpret.
     * Example: The client sends a GET request with an excessively long URL.
     */
    _414_URI_TOO_LONG(414, "URI Too Long", "Indicates that the URI requested by the client is longer than the server is willing to interpret."),

    /**
     * 415 Unsupported Media Type: Use when the request entity has a media type that the server or resource does not support.
     * Example: The client sends a request with a body in a format that is not supported by the server for the requested resource.
     */
    _415_UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type", "Indicates that the request entity has a media type which the server or resource does not support."),

    /**
     * 416 Range Not Satisfiable: Use when the client asks for a portion of the file, but the server cannot supply that portion.
     * Example: The client requests a range of bytes from a file, but the server cannot provide that range (perhaps it's too large).
     */
    _416_RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable", "Indicates that the client has asked for a portion of the file, but the server cannot supply that portion."),

    /**
     * 417 Expectation Failed: Use when the server cannot meet the requirements of the Expect request-header field.
     * Example: The client includes an Expect header in the request, but the server cannot meet the expectation.
     */
    /**
     * 418 I'm a teapot: Use as an Easter egg, not implemented in actual HTTP communications.
     * Example: As a part of an April Fools' joke, this code indicates the server is a teapot and cannot brew coffee.
     */
    _418_IM_A_TEAPOT(418, "I'm a teapot", "This code was defined in 1998 as one of the traditional IETF April Fools' jokes."),

    /**
     * 421 Misdirected Request: Use when the request was directed at a server that is not able to produce a response.
     * Example: The client sends a request to a server that is not configured to produce responses for the request URI.
     */
    _421_MISDIRECTED_REQUEST(421, "Misdirected Request", "Indicates that the request was directed at a server that is not able to produce a response."),

    /**
     * 422 Unprocessable Entity: Use when the request is well-formed but unable to be processed due to semantic errors.
     * Example: The client sends valid data, but the data does not meet the business logic requirements.
     */
    _422_UNPROCESSABLE_ENTITY(422, "Unprocessable Entity", "Indicates that the server understands the content type of the request entity, and the syntax of the request entity is correct, but it was unable to process the contained instructions."),

    /**
     * 423 Locked: Use when the resource that is being accessed is locked.
     * Example: The client attempts to modify a resource that is currently locked, preventing this action.
     */
    _423_LOCKED(423, "Locked", "Indicates that the resource that is being accessed is locked."),

    /**
     * 424 Failed Dependency: Use when the request failed due to failure of a previous request.
     * Example: The client's request cannot be processed because it depends on another request that failed.
     */
    _424_FAILED_DEPENDENCY(424, "Failed Dependency", "Indicates that the request failed due to failure of a previous request."),

    /**
     * 425 Too Early: Use when the server is unwilling to risk processing a request that might be replayed.
     * Example: The server is concerned about request replay and decides not to process the request prematurely.
     */
    _425_TOO_EARLY(425, "Too Early", "Indicates that the server is unwilling to risk processing a request that might be replayed."),

    /**
     * 426 Upgrade Required: Use when the server refuses to perform the request using the current protocol.
     * Example: The client sends a request over HTTP/1.1, but the server requires the client to switch to HTTP/2.
     */
    _426_UPGRADE_REQUIRED(426, "Upgrade Required", "Indicates that the server refuses to perform the request using the current protocol."),

    /**
     * 428 Precondition Required: Use when the server requires the request to be conditional.
     * Example: The server requires a conditional request (like If-Match) to ensure proper synchronization of data.
     */
    _428_PRECONDITION_REQUIRED(428, "Precondition Required", "Indicates that the server requires the request to be conditional."),

    /**
     * 429 Too Many Requests: Use when the user has sent too many requests in a given amount of time.
     * Example: The client is rate-limited and has sent too many requests in a short period.
     */
    _429_TOO_MANY_REQUESTS(429, "Too Many Requests", "Indicates that the user has sent too many requests in a given amount of time."),

    /**
     * 431 Request Header Fields Too Large: Use when the server is unwilling to process the request because its header fields are too large.
     * Example: The client sends a request with overly large headers, causing server issues or refusal to process.
     */
    _431_REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large", "Indicates that the server is unwilling to process the request because its header fields are too large."),

    /**
     * 451 Unavailable For Legal Reasons: Use when the user-agent requested a resource that cannot legally be provided.
     * Example: The client requests content that has been legally restricted, such as due to censorship or government-mandated blocks.
     */
    _451_UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons", "Indicates that the user-agent requested a resource that cannot legally be provided, such as a web page censored by a government."),

        // 5xx Server Error Responses
    /**
     * 500 Internal Server Error: Use when the server encounters an unexpected condition.
     * Example: The server experiences an error, such as a misconfiguration or an internal malfunction.
     */
    _500_INTERNAL_SERVER_ERROR(500, "Internal Server Error", "Indicates that the server has encountered a situation it doesn't know how to handle."),

    /**
     * 501 Not Implemented: Use when the server does not support the functionality required to fulfill the request.
     * Example: The client sends a request with a method that the server does not support or recognize.
     */
    _501_NOT_IMPLEMENTED(501, "Not Implemented", "Indicates that the request method is not supported by the server and cannot be handled."),

    /**
     * 502 Bad Gateway: Use when the server, acting as a gateway, receives an invalid response from the upstream server.
     * Example: A proxy server or gateway forwards the client's request but gets an invalid response from the next server in the chain.
     */
    _502_BAD_GATEWAY(502, "Bad Gateway", "Indicates that the server, while working as a gateway or proxy, received an invalid response from the upstream server."),

    /**
     * 503 Service Unavailable: Use when the server is not ready to handle the request.
     * Example: The server is down for maintenance or is overloaded, so it cannot process the request at this time.
     */
    _503_SERVICE_UNAVAILABLE(503, "Service Unavailable", "Indicates that the server is not ready to handle the request."),

    /**
     * 504 Gateway Timeout: Use when the server, acting as a gateway, did not receive a timely response from the upstream server.
     * Example: A proxy server did not receive a timely response from an upstream server and therefore cannot fulfill the client's request.
     */
    _504_GATEWAY_TIMEOUT(504, "Gateway Timeout", "Indicates that the server, while acting as a gateway or proxy, did not get a response in time from the upstream server."),

    /**
     * 505 HTTP Version Not Supported: Use when the server does not support the HTTP protocol version used in the request.
     * Example: The client sends a request using HTTP version 3.0, but the server only supports versions up to 2.0.
     */
    _505_HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported", "Indicates that the server does not support the HTTP protocol version used in the request."),

    /**
     * 506 Variant Also Negotiates: Use when the server has an internal configuration error.
     * Example: The server's response is not properly configured for content negotiation, leading to a circular reference.
     */
    _506_VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates", "Indicates that the server has an internal configuration error."),

    /**
     * 507 Insufficient Storage: Use when the server is unable to store the representation needed to complete the request.
     * Example: In a WebDAV request, the server cannot store the file due to insufficient storage space.
     */
    _507_INSUFFICIENT_STORAGE(507, "Insufficient Storage", "Indicates that the server is unable to store the representation needed to complete the request."),

    /**
     * 508 Loop Detected: Use when the server detects an infinite loop while processing the request.
     * Example: In WebDAV, the server terminates the operation due to an infinite loop encountered while processing the request.
     */
    _508_LOOP_DETECTED(508, "Loop Detected", "Indicates that the server detected an infinite loop while processing the request."),

    /**
     * 510 Not Extended: Use when further extensions to the request are required for the server to fulfill it.
     * Example: The server requires additional extensions to the request in order to fulfill it, and these are not provided by the client.
     */
    _510_NOT_EXTENDED(510, "Not Extended", "Indicates further extensions to the request are required for the server to fulfill it."),

    /**
     * 511 Network Authentication Required: Use when the client needs to authenticate to gain network access.
     * Example: The client needs to authenticate with the network (e.g., a Wi-Fi hotspot) before the request can proceed.
     */
    _511_NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required", "Indicates that the client needs to authenticate to gain network access.");

    private final int code;
    private final String reason;
    private final String description;

    /**
     * Constructor for HttpStatus.
     *
     * @param code        the HTTP status code.
     * @param reason      the reason phrase associated with the status code.
     * @param description a description of the status code.
     */
    MessageStatus(int code, String reason, String description) {
        this.code = code;
        this.reason = reason;
        this.description = description;
    }

    /**
     * Retrieves the HTTP status code.
     *
     * @return the status code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Retrieves the reason phrase associated with the status code.
     *
     * @return the reason phrase.
     */
    public String getReason() {
        return reason;
    }

    /**
     * Retrieves a description of the status code.
     *
     * @return the description of the status code.
     */
    public String getDescription() {
        return description;
    }
}
