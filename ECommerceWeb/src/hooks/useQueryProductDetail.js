import { useQuery } from "react-query";
import axios from "axios";

const findCategoryId = (category) => {
  if (category === "laptop-may-vi-tinh") {
    return 8095;
  }
};

const useQueryProducts = (category, productId) =>
  useQuery(["product", category, productId], async () => {
    return axios.get(
      `http://localhost:9000/products?category=${findCategoryId(
        category
      )}&productId=${productId}`
    );
  });

export default useQueryProducts;
