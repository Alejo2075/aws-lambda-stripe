# Stripe Lambda Payment (Java 17)

A simple **Java 17** project demonstrating how to process Stripe payments using **AWS Lambda**. This project uses **Maven**, **Stripe Java**, and **AWS Lambda** libraries.

## Features

- **Serverless Payment Processing**: No server management, thanks to AWS Lambda.
- **Stripe Integration**: Uses the official Stripe Java library for secure payment handling.
- **API Gateway Events**: Handles incoming requests via the AWS API Gateway proxy event format.

## Requirements

1. **Java 17**
2. **Maven 3.6+**
3. **Stripe Account** (secret key)
4. **AWS Account** (for Lambda deployment)


## Getting Started

1. **Clone or download** this repository.
2. **Set up your environment** (Java 17, Maven, AWS credentials, etc.).
3. **Configure `pom.xml`** if needed (e.g., changing groupId, artifactId, or dependencies).
4. **Build the project**:
   ```bash
   mvn clean package

5. **Deploy to AWS Lambda** using the -shade.jar file located in the target/ directory.

## Environment Variables

```
STRIPE_SECRET_KEY: Your Stripe secret key, set in the AWS Lambda console for secure usage.
```

## Testing Locally

Invoke the handler using an AWS SAM CLI or testing library.
You can also create a simple unit test that simulates the APIGatewayProxyRequestEvent.
