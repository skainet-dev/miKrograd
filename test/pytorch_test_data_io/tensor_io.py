from gguf import GGUFWriter, GGUFReader  # noqa: E402
import numpy as np  # noqa: E402


# Example usage:
def tensor_writer(gguf_file_path: str, tensor_name, tensor):
    # Example usage with a file
    gguf_writer = GGUFWriter(gguf_file_path, "llama")

    gguf_writer.add_tensor(tensor_name, tensor)
    gguf_writer.write_header_to_file()
    gguf_writer.write_kv_data_to_file()
    gguf_writer.write_tensors_to_file()


def tensor_reader(gguf_file_path: str):
    # Example usage with a file
    reader = GGUFReader(gguf_file_path)

    print("Tensors:")
    tensor_info_format = "{:<30} | Shape: {:<15} | Size: {:<12} | Quantization: {}"
    print(tensor_info_format.format("Tensor Name", "Shape", "Size", "Quantization"))
    print("-" * 80)
    return [(tensor.name, tensor.data) for tensor in reader.tensors]


if __name__ == '__main__':
    tensor_writer("example.gguf", "x", np.array([-4.0], dtype=np.float32))
    a = tensor_reader("example.gguf")
    print(a)

