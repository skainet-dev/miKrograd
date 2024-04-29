import torch
from pytorch_test_data_io.tensor_io import tensor_writer, tensor_reader

import unittest


class TestStringMethods(unittest.TestCase):

    def test_tensor_io(self):
        x = torch.Tensor([-4.0]).float32()
        tensor_writer("example.gguf", "x", x)
        (name, file_value_x) = tensor_reader("example.gguf")

        assert name == 'x'
        assert x.data == file_value_x.data.item()
