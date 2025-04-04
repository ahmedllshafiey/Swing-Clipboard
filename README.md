# Swing Clipboard

Swing Clipboard is a Java-based desktop application that monitors the system clipboard and maintains a history of copied text. The application provides a graphical user interface (GUI) built using Swing, allowing users to view, manage, and reuse clipboard entries.

---

## Features

- **Clipboard Monitoring**: Continuously listens for changes in the system clipboard.
- **Clipboard History**: Maintains a list of previously copied text entries.
- **GUI with Swing**: Displays clipboard history in a user-friendly interface.
- **Copy Back to Clipboard**: Allows users to copy selected entries back to the clipboard.
- **Customizable GUI**: Includes separators between clipboard items for better readability.

---

## Technical Details

### **Technologies Used**
- **Java**: Core programming language.
- **Swing**: For building the graphical user interface.
- **Maven**: For project management and build automation.

### **Project Structure**
```
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── clipboard/
│   │           ├── ClipboardListeners.java
│   │           └── ClipboardHistory.java
├── resources/
└── test/
```

### **Key Classes**
1. **`ClipboardListeners`**
   - Monitors the system clipboard for changes.
   - Implements `ClipboardOwner` to regain ownership of the clipboard.
   - Notifies the `EntryListener` when new text is copied.

2. **`ClipboardHistory`**
   - Implements the GUI for displaying clipboard history.
   - Uses a `JList` to show clipboard entries.
   - Provides functionality to copy selected entries back to the clipboard.

3. **`SwingClipboard`**
   - The main entry point of the application.
   - Initializes the GUI and starts the clipboard listener in a separate thread.

---

## How to Run

### **Prerequisites**
- Java Development Kit (JDK) 17 or higher.
- Maven installed on your system.

### **Build and Run**
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd Swing-Clipboard
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn exec:java
   ```

---

## Code Highlights

### **Clipboard Monitoring**
The `ClipboardListeners` class uses the `ClipboardOwner` interface to monitor clipboard changes:
```java
@Override
public void lostOwnership(Clipboard c, Transferable t) {
    try {
        sleep(200);
    } catch (Exception e) {
    }

    Transferable contents = clipboard.getContents(this);
    processContents(contents);
    regainOwnership(contents);
}
```

### **GUI with Swing**
The `ClipboardHistory` class creates a Swing-based GUI to display clipboard history:
```java
list = new JList<>(listModel);
listSelectionModel = list.getSelectionModel();
listSelectionModel.setSelectionMode(listSelectionModel.SINGLE_SELECTION);

JScrollPane listPane = new JScrollPane(list);
JPanel controlPane = new JPanel();
```

---

## Future Improvements
- Add support for non-text clipboard data (e.g., images, files).
- Implement search functionality for clipboard history.
- Add persistent storage to save clipboard history across sessions.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Author
Ahmed Sayed