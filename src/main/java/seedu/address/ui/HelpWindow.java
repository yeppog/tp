package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.commands.task.DoneTaskCommand;
import seedu.address.logic.commands.task.EditTaskCommand;
import seedu.address.logic.commands.task.FindTaskCommand;
import seedu.address.logic.commands.task.ListTaskCommand;
import seedu.address.logic.commands.task.PurgeTaskCommand;
import seedu.address.logic.parser.CommandSpecification;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2122s1-cs2103-f09-2.github.io/tp/UserGuide.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private static final List<CommandSpecification> ALL_COMMAND_SPECS = List.of(
            AddTaskCommand.COMMAND_SPECS,
            DeleteTaskCommand.COMMAND_SPECS,
            DoneTaskCommand.COMMAND_SPECS,
            EditTaskCommand.COMMAND_SPECS,
            FindTaskCommand.COMMAND_SPECS,
            ListTaskCommand.COMMAND_SPECS,
            PurgeTaskCommand.COMMAND_SPECS,
            AddTaskCommand.COMMAND_SPECS,
            ClearCommand.COMMAND_SPECS,
            DeleteCommand.COMMAND_SPECS,
            EditCommand.COMMAND_SPECS,
            ExitCommand.COMMAND_SPECS,
            FindCommand.COMMAND_SPECS,
            HelpCommand.COMMAND_SPECS,
            ListCommand.COMMAND_SPECS,
            RedoCommand.COMMAND_SPECS,
            UndoCommand.COMMAND_SPECS
    );

    @FXML
    private ListView<CommandSpecification> commandList;
    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        root.setMinWidth(600);
        root.setMinHeight(400);
        commandList.maxWidthProperty().bind(root.widthProperty());
        commandList.setItems(FXCollections.observableList(ALL_COMMAND_SPECS));
        commandList.setCellFactory((ListView<CommandSpecification> param) -> new ListCell<>() {
            @Override
            protected void updateItem(CommandSpecification specs, boolean empty) {
                super.updateItem(specs, empty);
                if (!empty) {
                    Label title = new Label(specs.getWord());
                    Label description = new Label(specs.getDescription());
                    Label usage = new Label(specs.getCommandUsage());
                    Label format = new Label("Format");

                    title.getStyleClass().add("label-header-small");
                    usage.getStyleClass().add("mono");
                    format.getStyleClass().add("italics");
                    format.setPadding(new Insets(8, 0, 0, 0));

                    // Enable wrap text
                    description.setWrapText(true);
                    usage.setWrapText(true);

                    VBox vBox = new VBox(
                            title,
                            new TextFlow(description),
                            format,
                            new TextFlow(usage)
                    );
                    vBox.setPadding(new Insets(16));

                    // Restrict width of text to allow wrap text
                    description.maxWidthProperty().bind(commandList.maxWidthProperty().subtract(32));
                    vBox.maxWidthProperty().bind(commandList.maxWidthProperty().subtract(32));
                    usage.maxWidthProperty().bind(commandList.maxWidthProperty().subtract(32));


                    if (!specs.getExampleCommands().isEmpty()) {
                        Label example = new Label("Example");
                        example.getStyleClass().add("italics");

                        VBox exampleUsage = new VBox(example);
                        exampleUsage.setPadding(new Insets(8, 0, 0, 0));

                        specs.getExampleCommands().stream().map(ec -> {
                            Label label = new Label(ec);
                            label.getStyleClass().add("mono");
                            label.setWrapText(true);
                            return label;
                        }).map(TextFlow::new)
                                .forEach(exampleUsage.getChildren()::add);
                        vBox.getChildren().add(exampleUsage);
                    }

                    setGraphic(vBox);
                }
            }
        });
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Open the user's Desktop Browser
     */
    @FXML
    private void openUrl() {
        try {
            Desktop.getDesktop().browse(new URL(USERGUIDE_URL).toURI());
        } catch (MalformedURLException | URISyntaxException e) {
            logger.info("Invalid URL");
        } catch (IOException ioe) {
            logger.info(ioe.getMessage());
        }
    }
}
