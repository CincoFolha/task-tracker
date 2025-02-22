
# Task Tracker CLI

## Installation

### **Clone the repository**
```bash
git clone https://github.com/CincoFolha/task-tracker.git
cd task-tracker
```

### **Build the project**
Usando Gradle Wrapper:
```bash
./gradlew build
```
Ou no Windows:
```bash
gradlew.bat build
```
### **Run the application**
```bash
./gradlew run
```


## Usage
```bash
# Adding a new task
./gradlew run --args="add 'Buy groceries'"
# Output: Task added successfully (ID: 1)

# Updating and deleting tasks
./gradlew run --args="update 1 'Buy groceries and cook dinner'"
./gradlew run --args="delete 1"

# Marking a task as in progress or done
./gradlew run --args="mark-in-progress 1"
./gradlew run --args="mark-done 1"

# Linting all tasks
./gradlew run --args="list"

# Listing tasks by status
./gradlew run --args="list done"
./gradlew run --args="list todo"
./gradlew run --args="list in-progress"
```

## **Technologies Used**
- **Java**
- **Gradle**

## URL
https://roadmap.sh/projects/task-tracker
