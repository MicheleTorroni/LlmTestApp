import scala.swing.{BoxPanel, *}
import scala.swing.event.ButtonClicked
import swing.Swing.pair2Dimension

trait LlmTestView extends MainFrame:
  def setOutputText(outputText: String): Unit
  def setOpenAiApiKey(apiKey: String): Unit
  def setAddressField(address: String): Unit
  def setCommandField(command: String): Unit
  def addObserver(viewObserver: LlmTestViewObserver): Unit

object LlmTestView:
  def apply(models: List[String], programmingLanguages: List[String]):LlmTestView = LlmTestViewImpl(models, programmingLanguages)

  private case class LlmTestViewImpl(models: List[String], programmingLanguages: List[String]) extends LlmTestView:
    var viewObservers : List[LlmTestViewObserver] = List()
    title = "LLM Code Generator"
    preferredSize = (900, 600)

    val blankSpace: Label = new Label()

    val inputLabel: Label = new Label("Input: ")
    val inputField: TextArea = new TextArea:
      rows = 30
      columns = 50
      lineWrap = true
      wordWrap = true

    val outputLabel: Label = new Label("Output: ")
    val outputField: TextArea = new TextArea:
      rows = 30
      columns = 50
      editable = false
      lineWrap = true
      wordWrap = true

    val openAiLabel: Label = new Label("OPEN AI SETTINGS")
    val apiKeyLabel: Label = new Label("ApiKey:       ")
    val openAiApiKeyField: TextField = new TextField("My Open Ai Key")
    val createOpenAiServiceButton: Button = new Button("Open Ai Service")

    val localLabel: Label = new Label("LOCAL SETTINGS")
    val commandLabel: Label = new Label("Command: ")
    val commandField: TextField = new TextField()
    val runCommand: Button = new Button("Run Command")
    val addressLabel: Label = new Label("Address:    ")
    val addressField: TextField = new TextField()
    val createLocalServiceButton: Button = new Button("Local Service")

    val generalLabel: Label = new Label("GENERAL SETTINGS")
    val modelLabel = new Label("Model:       ")
    val modelsComboBox = new ComboBox(models)
    val languageLabel = new Label("Language: ")
    val programmingLanguagesComboBox = new ComboBox(programmingLanguages)
    val produceCodeButton: Button = new Button:
      text = "PRODUCE CODE"
      enabled = false

    contents = new BoxPanel(Orientation.Horizontal):
      contents += new BoxPanel(Orientation.Horizontal):
        contents += new BoxPanel(Orientation.Vertical):
          contents += inputLabel
          contents += new ScrollPane(inputField)
        contents += new BoxPanel(Orientation.Vertical):
          contents += outputLabel
          contents += new ScrollPane(outputField)
        contents += new BoxPanel(Orientation.Vertical):
          contents += new GridPanel(2, 1):
            contents += openAiLabel
            contents += new BoxPanel(Orientation.Horizontal):
              contents += apiKeyLabel
              contents += openAiApiKeyField
              contents += new GridPanel(1, 1):
                contents += createOpenAiServiceButton
          contents += new GridPanel(3, 1):
            contents += localLabel
            contents += new BoxPanel(Orientation.Horizontal):
              contents += commandLabel
              contents += commandField
              contents += new GridPanel(1, 1):
                contents += runCommand
            contents += new BoxPanel(Orientation.Horizontal):
              contents += addressLabel
              contents += addressField
              contents += new GridPanel(1, 1):
                contents += createLocalServiceButton
          contents += new GridPanel(5, 1):
            contents += generalLabel
            contents += new BoxPanel(Orientation.Horizontal):
              contents += modelLabel
              contents += modelsComboBox
            contents += new BoxPanel(Orientation.Horizontal):
              contents += languageLabel
              contents += programmingLanguagesComboBox
            contents += blankSpace
            contents += produceCodeButton

    listenTo(produceCodeButton, createOpenAiServiceButton, createLocalServiceButton, runCommand)

    reactions += {
      case ButtonClicked(`produceCodeButton`) =>
        viewObservers.foreach(obs =>
          obs.produceCode(inputField.text, modelsComboBox.selection.item, programmingLanguagesComboBox.selection.item))
        produceCodeButton.enabled = false
      case ButtonClicked(`createOpenAiServiceButton`) =>
        viewObservers.foreach(obs =>
          obs.createOpenAiService(openAiApiKeyField.text))
        produceCodeButton.enabled = true
        createOpenAiServiceButton.enabled = false
        createLocalServiceButton.enabled = true
      case ButtonClicked(`createLocalServiceButton`) =>
        viewObservers.foreach(obs =>
          obs.createLocalService(addressField.text))
        produceCodeButton.enabled = true
        createLocalServiceButton.enabled = false
        createOpenAiServiceButton.enabled = true
      case ButtonClicked(`runCommand`) =>
        viewObservers.foreach(obs => obs.runCommand(commandField.text))
    }

    def setOutputText(outputText: String): Unit =
      outputField.text = outputText
      produceCodeButton.enabled = true
    def setOpenAiApiKey(apiKey: String): Unit = openAiApiKeyField.text = apiKey
    def setAddressField(address: String): Unit = addressField.text = address
    def setCommandField(command: String): Unit = commandField.text = command
    def addObserver(viewObserver: LlmTestViewObserver): Unit = viewObservers = viewObserver :: viewObservers
