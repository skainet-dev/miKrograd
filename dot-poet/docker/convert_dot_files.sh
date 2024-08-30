#!/bin/sh

# Usage function
usage() {
  echo "Usage: $0 [-f <file>] [-p <pattern>] [-r <directory>] <output-format>"
  echo "    -f <file>          Convert a single .dot file."
  echo "    -p <pattern>       Convert .dot files matching a pattern."
  echo "    -r <directory>     Recursively convert .dot files in the specified directory."
  exit 1
}

# Parse command-line options
while getopts "f:p:r:" opt; do
  case $opt in
    f) FILE=$OPTARG ;;
    p) PATTERN=$OPTARG ;;
    r) DIRECTORY=$OPTARG ;;
    *) usage ;;
  esac
done

# Shift the arguments so that $1 now refers to the output format
shift $((OPTIND -1))

# Check if output format is provided
if [ -z "$1" ]; then
  echo "Output format is required."
  usage
fi

OUTPUT_FORMAT=$1
echo "Output format is $OUTPUT_FORMAT."

# Handle different modes
if [ -n "$FILE" ]; then
  # Convert a single file
  if [ ! -f "$FILE" ]; then
    echo "File not found: $FILE"
    exit 1
  fi
  dot -T"$OUTPUT_FORMAT" -O "$FILE"

elif [ -n "$DIRECTORY" ] && [ -n "$PATTERN" ]; then
  # Recursively find and convert .dot files in the directory matching the pattern
  if [ ! -d "$DIRECTORY" ]; then
    echo "Directory not found: $DIRECTORY"
    exit 1
  fi
  find "$DIRECTORY" -type f -name "$PATTERN" | while read -r dotfile; do
    dot -T"$OUTPUT_FORMAT" -O "$dotfile"
  done

elif [ -n "$DIRECTORY" ]; then
  # Recursively find and convert all .dot files in the directory
  if [ ! -d "$DIRECTORY" ]; then
    echo "Directory not found: $DIRECTORY"
    exit 1
  fi
  find "$DIRECTORY" -type f -name "*.dot" | while read -r dotfile; do
    dot -T"$OUTPUT_FORMAT" -O "$dotfile"
  done

elif [ -n "$PATTERN" ]; then
  # Convert files matching a pattern in the current directory
  for dotfile in "$PATTERN"; do
    if [ -f "$dotfile" ]; then
      dot -T"$OUTPUT_FORMAT" -O "$dotfile"
    fi
  done

else
  usage
fi
