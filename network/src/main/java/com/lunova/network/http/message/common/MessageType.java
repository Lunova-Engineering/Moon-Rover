package com.lunova.network.http.message.common;

/**
 * Enum representing all HTTP request methods. HTTP defines a set of request methods to indicate the
 * desired action to be performed for a given resource.
 */
public enum MessageType {
  /**
   * GET: Use to request a representation of the specified resource. Example: Retrieving a webpage
   * or an image. GET requests should not cause any side effects on the server.
   */
  GET(
      false,
      true,
      true,
      true,
      true,
      "The GET method requests a representation of the specified resource. Requests using GET should only retrieve data."),

  /**
   * HEAD: Use to request the headers that would be returned if the HEAD request's URL was instead
   * requested with the HTTP GET method. Example: Checking what a GET request to the same URL would
   * return, without actually downloading the entire body of the response.
   */
  HEAD(
      false,
      false,
      true,
      true,
      false,
      "The HEAD method asks for a response identical to a GET request, but without the response body."),

  /**
   * POST: Use to submit an entity to the specified resource, often causing a change in state or
   * side effects on the server. Example: Submitting form data or uploading a file. Data sent with
   * POST will be stored on the server.
   */
  POST(
      true,
      true,
      false,
      false,
      true,
      "The POST method submits an entity to the specified resource, often causing a change in state or side effects on the server."),

  /**
   * PUT: Use to replace all current representations of the target resource with the request
   * payload. Example: Uploading a file or updating a resource. Unlike POST, PUT is idempotent,
   * meaning repeating the request will have the same effect.
   */
  PUT(
      true,
      true,
      false,
      true,
      false,
      "The PUT method replaces all current representations of the target resource with the request payload."),

  /**
   * DELETE: Use to delete the specified resource. Example: Removing a webpage or an image. DELETE
   * requests are expected to have an effect on the server.
   */
  DELETE(false, true, false, true, false, "The DELETE method deletes the specified resource."),

  /**
   * CONNECT: Use to establish a tunnel to the server identified by the target resource. Example:
   * Used by HTTP proxies to dynamically switch to being a TCP/IP tunnel.
   */
  CONNECT(
      false,
      true,
      false,
      false,
      false,
      "The CONNECT method establishes a tunnel to the server identified by the target resource."),

  /**
   * OPTIONS: Use to describe the communication options for the target resource. Example:
   * Determining which HTTP methods are supported by a web server.
   */
  OPTIONS(
      false,
      true,
      true,
      true,
      false,
      "The OPTIONS method describes the communication options for the target resource."),

  /**
   * TRACE: Use to perform a message loop-back test along the path to the target resource. Example:
   * Echoing back the received request so that a client can see what intermediate servers are adding
   * or changing in the request.
   */
  TRACE(
      false,
      true,
      true,
      true,
      false,
      "The TRACE method performs a message loop-back test along the path to the target resource."),

  /**
   * PATCH: Use to apply partial modifications to a resource. Example: Making a partial update to a
   * resource, like modifying specific fields of a resource without replacing the entire resource.
   */
  PATCH(
      true,
      true,
      false,
      false,
      false,
      "The PATCH method applies partial modifications to a resource.");

  private final boolean hasRequestBody;
  private final boolean hasResponseBody;
  private final boolean isSafe;
  private final boolean isIdempotent;
  private final boolean allowedInForms;
  private final String description;

  /**
   * Constructor for HttpRequestType.
   *
   * @param hasRequestBody indicates if the request type has a request body.
   * @param hasResponseBody indicates if the request type expects a response body.
   * @param isSafe indicates if the request type is considered safe.
   * @param isIdempotent indicates if the request type is idempotent.
   * @param allowedInForms indicates if the request type is allowed in HTML forms.
   * @param description a description of the request type.
   */
  MessageType(
      boolean hasRequestBody,
      boolean hasResponseBody,
      boolean isSafe,
      boolean isIdempotent,
      boolean allowedInForms,
      String description) {
    this.hasRequestBody = hasRequestBody;
    this.hasResponseBody = hasResponseBody;
    this.isSafe = isSafe;
    this.isIdempotent = isIdempotent;
    this.allowedInForms = allowedInForms;
    this.description = description;
  }

  /**
   * Checks if the request type has a request body.
   *
   * @return true if it has a request body, false otherwise.
   */
  public boolean hasRequestBody() {
    return hasRequestBody;
  }

  /**
   * Checks if the request type expects a response body.
   *
   * @return true if it expects a response body, false otherwise.
   */
  public boolean hasResponseBody() {
    return hasResponseBody;
  }

  /**
   * Checks if the request type is considered safe.
   *
   * @return true if it is safe, false otherwise.
   */
  public boolean isSafe() {
    return isSafe;
  }

  /**
   * Checks if the request type is idempotent.
   *
   * @return true if it is idempotent, false otherwise.
   */
  public boolean isIdempotent() {
    return isIdempotent;
  }

  /**
   * Checks if the request type is allowed in HTML forms.
   *
   * @return true if allowed, false otherwise.
   */
  public boolean isAllowedInForms() {
    return allowedInForms;
  }

  /**
   * Retrieves a description of the request type.
   *
   * @return the description of the request type.
   */
  public String getDescription() {
    return description;
  }
}
