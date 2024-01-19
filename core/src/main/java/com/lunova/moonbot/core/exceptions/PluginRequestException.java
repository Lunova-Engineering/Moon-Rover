package com.lunova.moonbot.core.exceptions;

/**
 * Represents an exception that is thrown when there is an issue with a plugin request. This class
 * extends the Java {@link Exception} class and provides a single constructor that allows for the
 * specification of an error message. Use this exception to indicate failures or issues specifically
 * related to plugin requests, such as invalid plugin operations, communication failures, or other
 * related problems.
 *
 * <p>Instances of this exception are thrown to indicate that a method has encountered an
 * exceptional condition involving a plugin request.
 *
 * <p>Usage examples include:
 *
 * <ul>
 *   <li>Throwing this exception when a plugin cannot be loaded due to missing dependencies.
 *   <li>Throwing this exception when a plugin fails to respond to a communication or command.
 *   <li>Throwing this exception when there are security or permission issues with a plugin action.
 * </ul>
 *
 * <p>This exception should be caught and handled appropriately by the caller to ensure that the
 * error condition is reported and managed in line with the application's error handling policies.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.14.2023
 */
public class PluginRequestException extends Exception {

  /**
   * Constructs a new PluginRequestException with the specified detail message. The detail message
   * is saved for later retrieval by the {@link Throwable#getMessage()} method.
   *
   * @param message the detail message, which provides a description of the exception. It is saved
   *     for later retrieval by the {@link Throwable#getMessage()} method.
   */
  public PluginRequestException(String message) {
    super(message);
  }

  public PluginRequestException(String message, Exception e) {
    super(message, e);
  }
}
