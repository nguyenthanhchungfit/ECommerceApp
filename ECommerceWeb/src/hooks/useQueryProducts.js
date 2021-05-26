import { useQuery } from "react-query";
import axios from "axios";

const useQueryProducts = (category, page) =>
  useQuery(["product", category, page], async () => {
    return axios
      .get(`http://localhost:9000/api/products?category=${category}&page=${page}`)
      .then((response) => response.data);
  });

export default useQueryProducts;
