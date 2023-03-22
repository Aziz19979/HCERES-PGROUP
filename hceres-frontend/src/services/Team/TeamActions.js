import axios from "axios";
import MyGlobalVar from "../MyGlobalVar";
import {API_URL} from "../../constants";

export const fetchListTeams = async () => {
    if (!MyGlobalVar.listeTeams) {
        const response = await axios.get(API_URL + "/Teams");
        MyGlobalVar.listeTeams = response.data;
    }
    return MyGlobalVar.listeTeams;
}