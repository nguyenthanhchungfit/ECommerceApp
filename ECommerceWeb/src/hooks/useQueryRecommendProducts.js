import { useQuery } from "react-query";
import axios from "axios";

const useQueryRecommendProducts = (userId) =>
  useQuery(["recommend_product", userId], async () => {
    return axios
      .get(`http://localhost:9000/api/recommend/product?userId=${userId}`)
      .then((response) => response.data);
  });

export default useQueryRecommendProducts;
