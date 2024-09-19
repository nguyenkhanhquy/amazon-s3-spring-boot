# Amazon S3 - Spring Boot
Upload File to S3 with AWS Java SDK - Java Spring Boot Web App

## Prerequisites
- Java Development Kit (JDK) 21 or later
- Maven: 3.9.9
- Spring Boot: 3.3.3
- Ensure you have a `.env` file in the root directory of your project. This file should contain all necessary environment variables for configuring AWS services.

## Setting Up Environment Variables

Create or edit the `.env` file in the root directory of your project with the following content:

```plaintext
# AWS Configuration
AWS_REGION=your-aws-region
AWS_BUCKET=your-s3-bucket-name
AWS_ACCESS_KEY_ID=your-aws-access-key-id
AWS_SECRET_ACCESS_KEY=your-aws-secret-access-key
AWS_URL_FOLDER=your-s3-url-folder
