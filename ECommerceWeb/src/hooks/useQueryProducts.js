import { useQuery } from "react-query";
import axios from "axios";

const useQueryProducts = (category) =>
  useQuery(["product", category], async () => {
    return axios.get(`http://localhost:9000/api/products?category=${category}`);
  });

export default useQueryProducts;
