import java.util.List;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * Base class for creating plugins.
 *
 * <p>This abstract class serves as the foundation for all plugins in the application. It provides
 * basic properties like name and version, and defines the structure for plugin installation and
 * command registration.
 *
 * @author Drake - <a href="https://github.com/metorrite">GitHub</a>
 * @since 11.25.2023
 */
public abstract class Plugin {

  /** The name of the plugin. */
  private final String name;

  /** The version of the plugin. */
  private final String version;

  /**
   * Constructs a new Plugin instance with the specified name and version.
   *
   * @param name The name of the plugin.
   * @param version The version of the plugin.
   */
  public Plugin(String name, String version) {
    this.name = name;
    this.version = version;
  }

  /**
   * Installs the plugin to a specified server.
   *
   * <p>This method is responsible for initializing the plugin and registering its components within
   * the specified server context.
   *
   * @param serverId The identifier of the server where the plugin is to be installed.
   * @todo Implement the installation logic that interfaces with the core module.
   */
  public void installPlugin(String serverId) {}

  /**
   * Registers the commands associated with this plugin.
   *
   * <p>This method should be overridden by subclasses to define specific slash commands for the
   * plugin.
   *
   * @return A list of {@link SlashCommandData} representing the commands for this plugin.
   */
  public abstract List<SlashCommandData> registerCommands();

  /**
   * Gets the name of the plugin.
   *
   * @return The name of the plugin.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the version of the plugin.
   *
   * @return The version of the plugin.
   */
  public String getVersion() {
    return version;
  }
}
