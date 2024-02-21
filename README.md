# RepoExplorer

The `RepoExplorer` project is a Spring Boot application designed to interact with the GitHub API to retrieve information about repositories and branches for specified GitHub users. The application filters out forks and aggregates branch data for each repository.

## Features

- Retrieving a list of repositories (excluding forks) for a given GitHub user.
- Aggregating branch information for each repository.
- Filtering repositories to exclude forks.

## Technologies

- **Spring Boot** - Framework for creating applications and microservices.
- **Maven** - Project and dependency management tool.
- **Lombok** - Library for automatic generation of getters, setters, constructors, etc.
- **Mockito** - Framework for creating mock objects in unit tests.

## Requirements

- Java JDK 11 or newer.
- Maven 3.6.3 or newer.

To retrieve a list of repositories for GitHub user use the following endpoint: 
 - **GET /github/repos?user=<username>**


