import { useMutation } from "react-query";
import axios from "axios";

const useMutationCheckout = () =>
  useMutation(async ({ userId, orderItems, subTotal }) => {
    return axios.post(`http://localhost:9000/order/add`, {
      userId: userId,
      orderItems: orderItems,
      subTotal: subTotal
    });
  });

export default useMutationCheckout;
