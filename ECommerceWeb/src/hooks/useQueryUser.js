
import { useQuery } from "react-query";
import axios from "axios";

const useQueryUser = (id) =>
  useQuery(["user", id], async () => {
    return axios.get(`http://localhost:9000/user?id=${id}`);
  });

export default useQueryUser;