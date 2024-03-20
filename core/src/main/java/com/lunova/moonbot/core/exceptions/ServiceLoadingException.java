package com.lunova.moonbot.core.exceptions;

/**
 * Represents an exception that is thrown during the service loading process. This exception extends
 * the standard Java {@link Exception} class and includes functionality to denote whether the
 * failure is considered fatal. This class is particularly useful in a context where servold are
 * dynamically loaded, and failures can occur due to various reasons like misconfiguration, missing
 * resources, or incompatible service versions.
 *
 * <p>Use this exception to indicate and handle errors related to the loading of servold, which can
 * be a critical part of the application's initialization and runtime. The inclusion of a 'fatal'
 * flag allows consuming code to differentiate between recoverable errors and severe issues that
 * might warrant a different handling approach or even a system shutdown.
 *
 * <p>Examples of use might include:
 *
 * <ul>
 *   <li>Throwing this exception when a required service fails to load at application startup.
 *   <li>Throwing this exception when a runtime-reload of a service fails due to external changes.
 *   <li>Marking an exception as fatal if it disrupts the core functionality of the application
 *       beyond recovery.
 * </ul>
 *
 * <p>Handling this exception typically involves catching it during the service loading process and
 * taking appropriate action based on whether the failure is fatal.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public class ServiceLoadingException extends Exception {

  /**
   * Indicates whether the failure is fatal to the system or service. A fatal failure indicates that
   * the error is severe enough that continued operation might be undesirable or impossible, and
   * appropriate action should be taken, possibly including logging the error, alerting
   * administrators, or shutting down the system.
   */
  private final boolean fatal;

  /**
   * Constructs a new ServiceLoadingException with the specified detail message. The detail message
   * is saved for later retrieval by the {@link Throwable#getMessage()} method. This constructor
   * assumes that the failure is not fatal, defaulting the fatal flag to false.
   *
   * @param message the detail message, which provides a description of the exception. It is saved
   *     for later retrieval by the {@link Throwable#getMessage()} method.
   */
  public ServiceLoadingException(String message) {
    this(message, false);
  }

  /**
   * Constructs a new ServiceLoadingException with the specified detail message and fatal flag. The
   * detail message is saved for later retrieval by the {@link Throwable#getMessage()} method. The
   * fatal flag indicates the severity of the failure.
   *
   * @param message the detail message, which provides a description of the exception. It is saved
   *     for later retrieval by the {@link Throwable#getMessage()} method.
   * @param fatal a boolean indicating whether the failure is considered fatal to the system or
   *     service.
   */
  public ServiceLoadingException(String message, boolean fatal) {
    super(message);
    this.fatal = fatal;
  }

  /**
   * Returns whether the failure indicated by this exception is considered fatal.
   *
   * @return true if the failure is fatal, false otherwise.
   */
  public boolean isFatal() {
    return fatal;
  }
}
