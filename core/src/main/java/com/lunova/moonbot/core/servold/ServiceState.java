package com.lunova.moonbot.core.servold;

/**
 * Represents the possible states of a service within the bot application. Each state indicates a
 * specific phase in the service lifecycle, from initialization to shut down.
 *
 * <p>Services transition through these states as they are started, initialized, and potentially
 * shut down. This enumeration aids in managing and understanding the current status of any given
 * service in the system.
 */
public enum ServiceState {
  /** Service has not been initialized yet. */
  NOT_INITIALIZED,

  /** Service is in the process of initializing. */
  INITIALIZING,

  /** Service has been successfully initialized. */
  INITIALIZED,

  /** Service failed to initialize properly. */
  INITIALIZATION_FAILED,

  /**
   * Service is in the process of shutting down. This state is reserved for future use when service
   * might need to gracefully shut down due to an error or system shutdown.
   */
  SHUTTING_DOWN, // Future state for servold that encounter a fatal error and need to shut down

  /**
   * Service has been shut down. This state indicates that the service has completed its shutdown
   * process and is no longer operational. It may need to be restarted to become functional again.
   */
  SHUT_DOWN // Future state for a service that has shut down and needs to be restarted.
}
