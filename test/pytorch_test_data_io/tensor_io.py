from typing import List, Tuple

from gguf import GGUFWriter, GGUFReader  # noqa: E402
import numpy as np  # noqa: E402
import torch  # noqa: E402


# Example usage:
def tensor_writer(gguf_file_path: str, tensor_name, tensor: torch.Tensor):
    # Example usage with a file
    gguf_writer = GGUFWriter(gguf_file_path, "llama")

    gguf_writer.add_tensor(tensor_name, tensor.cpu().numpy(), np.array(tensor.shape))
    gguf_writer.write_header_to_file()
    gguf_writer.write_kv_data_to_file()
    gguf_writer.write_tensors_to_file()

    gguf_writer.close()


def to_tensor(flatten_data, shape) -> torch.Tensor:
    tensor_shape = tuple(shape[::-1].copy())  # Convert shape memmap to a tuple, which should be (3, 4)

    # Reshape the flat data according to the loaded shape
    reshaped_data = np.reshape(flatten_data, tensor_shape)

    return torch.from_numpy(reshaped_data.copy())


def tensor_reader(gguf_file_path: str) -> List[Tuple[str, torch.Tensor]]:
    # Example usage with a file
    reader = GGUFReader(gguf_file_path)

    print("Tensors:")
    tensor_info_format = "{:<30} | Shape: {:<15} | Size: {:<12} | Quantization: {}"
    print(tensor_info_format.format("Tensor Name", "Shape", "Size", "Quantization"))
    print("-" * 80)
    return [(tensor.name, to_tensor(tensor.data, tensor.shape)) for tensor in reader.tensors]


if __name__ == '__main__':
    tensor_writer("example.gguf", "x", np.array([-4.0], dtype=np.float32))
    (a, b) = tensor_reader("example.gguf")
    print(a)
