import numpy as np
import torch
from pytorch_test_data_io.tensor_io import tensor_writer, tensor_reader

import unittest


class TestTensorIoWithGguf(unittest.TestCase):

    def test_simple_tensor_io(self):
        x = torch.Tensor(np.array([-4.0], dtype=np.float32))
        tensor_writer("example.gguf", "x", x)

        tensors = tensor_reader("example.gguf")
        name = tensors[0][0]
        file_value_x = tensors[0][1]

        assert len(tensors) == 1
        assert name == 'x'
        assert x.data == file_value_x.data
        assert x.shape == file_value_x.shape

    def test_vector_tensor_io(self):
        x = torch.Tensor(np.array([-4.0, 5.0], dtype=np.float32))
        tensor_writer("example1.gguf", "x", x)

        tensors = tensor_reader("example1.gguf")
        name = tensors[0][0]
        file_value_x = tensors[0][1]

        assert len(tensors) == 1
        assert name == 'x'
        assert torch.equal(x, file_value_x)
        assert x.shape == file_value_x.shape

    def test_matrix_tensor_io(self):
        x = torch.Tensor(np.array([[1, 2, 3], [4, 5, 6]], dtype=np.float32))
        tensor_writer("example2.gguf", "x", x)

        tensors = tensor_reader("example2.gguf")
        name = tensors[0][0]
        file_value_x = tensors[0][1]

        assert len(tensors) == 1
        assert name == 'x'
        assert torch.equal(x, file_value_x)
        assert x.shape == file_value_x.shape

    def test_tensor_io(self):
        x = torch.Tensor(np.array([
            [
                [1, 2, 3], [4, 5, 6], [7, 8, 9], [10, 11, 12]
            ],
            [
                [11, 12, 13], [14, 15, 16], [17, 18, 19], [17, 18, 19]
            ]
        ], dtype=np.float32))

        tensor_writer("example3.gguf", "x", x)

        tensors = tensor_reader("example3.gguf")
        name = tensors[0][0]
        file_value_x = tensors[0][1]

        assert len(tensors) == 1
        assert name == 'x'
        assert torch.equal(x, file_value_x)
        assert x.shape == file_value_x.shape
