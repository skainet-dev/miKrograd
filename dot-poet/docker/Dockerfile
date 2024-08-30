# Use a specific version of Alpine as the base image
FROM alpine:3.18

# Install Graphviz and any necessary utilities
RUN apk add --no-cache graphviz

# Copy the script into the container
COPY convert_dot_files.sh /usr/local/bin/convert_dot_files.sh

# Make the script executable
RUN chmod +x /usr/local/bin/convert_dot_files.sh

# Set the working directory
WORKDIR /workspace

# Set the default command to run the script
ENTRYPOINT ["/usr/local/bin/convert_dot_files.sh"]
