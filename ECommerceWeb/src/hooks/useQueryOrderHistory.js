import { useQuery } from "react-query";
import axios from "axios";

const useQueryOrderHistory = (userId) =>
  useQuery(["orderDetail", userId], async () => {
    return axios.get(`http://localhost:9000/order/detail?userId=${userId}`);
  });

export default useQueryOrderHistory;