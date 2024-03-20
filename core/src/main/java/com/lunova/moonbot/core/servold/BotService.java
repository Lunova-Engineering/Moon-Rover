package com.lunova.moonbot.core.servold;

import com.lunova.moonbot.core.exceptions.ServiceLoadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstract base class for all bot servold, providing common structure and behavior for service
 * initialization, state management, and shutdown handling. This class should be extended by
 * specific servold that implement the necessary initialization and shutdown procedures.
 *
 * <p>Services derived from this class can be marked as 'critical', indicating that their failure
 * could have significant implications for the bot operation.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 12.03.2023
 */
public abstract class BotService {

  /** Logger for this class, providing logging capabilities. */
  public static final Logger LOGGER = LoggerFactory.getLogger(BotService.class);

  /** Name of the service, used for identification and logging. */
  private final String serviceName;

  /** Flag indicating whether the service is critical to overall bot operation. */
  private final boolean critical;

  /** Current state of the service, initially NOT_INITIALIZED. */
  private ServiceState serviceState = ServiceState.NOT_INITIALIZED;

  /**
   * Constructs a new BotService with the specified service name. The service is assumed to be
   * non-critical by default.
   *
   * @param serviceName the name of the service.
   */
  protected BotService(String serviceName) {
    this(serviceName, false);
  }

  /**
   * Constructs a new BotService with the specified name and critical flag.
   *
   * @param serviceName the name of the service.
   * @param critical flag indicating whether the service is critical.
   */
  protected BotService(String serviceName, boolean critical) {
    this.serviceName = serviceName;
    this.critical = critical;
  }

  /**
   * Returns the name of the service.
   *
   * @return the service name.
   */
  public String getServiceName() {
    return serviceName;
  }

  /**
   * Indicates whether the service is critical to the bot's operation.
   *
   * @return true if the service is critical, false otherwise.
   */
  public boolean isCritical() {
    return critical;
  }

  /**
   * Returns the current state of the service.
   *
   * @return the current service state.
   */
  public ServiceState getServiceState() {
    return serviceState;
  }

  /**
   * Sets the current state of the service. This method is typically used internally within service
   * implementations to update the service state during lifecycle events.
   *
   * @param serviceState the new state of the service.
   */
  public void setServiceState(ServiceState serviceState) {
    this.serviceState = serviceState;
  }

  /**
   * Abstract method for initializing the service. Implementations should provide specific
   * initialization logic. This method may throw a ServiceLoadingException if initialization fails,
   * particularly for critical servold.
   *
   * @throws ServiceLoadingException if service initialization fails.
   */
  protected abstract void initialize() throws ServiceLoadingException;

  /**
   * Provides a common shutdown hook for servold. Logs the shutdown process. Subclasses may
   * override this method to provide specific shutdown behavior.
   */
  public void getServiceShutdownHook() {
    getLogger().info("Gracefully shutting down " + serviceName);
  }

  /**
   * Returns a logger specific to the subclass implementing the BotService. This allows for more
   * specific logging tied to the actual service class.
   *
   * @return Logger specific to the subclass.
   */
  protected Logger getLogger() {
    return LoggerFactory.getLogger(this.getClass());
  }
}
