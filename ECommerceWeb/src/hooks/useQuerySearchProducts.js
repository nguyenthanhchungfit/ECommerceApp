import { useQuery } from "react-query";
import axios from "axios";

const useQuerySearchProducts = (match) =>
  useQuery(["search_product", match], async () => {
    return axios
      .get(`http://localhost:9000/api/search?sseach=${match}`)
      .then((response) => response.data);
  });

export default useQuerySearchProducts;
