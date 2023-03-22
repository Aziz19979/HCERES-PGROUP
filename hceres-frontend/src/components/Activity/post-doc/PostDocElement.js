import {ListGroup} from "react-bootstrap";

const PostDocElement = (props) =>
    props.targetPostDoc && props.targetPostDoc.postDoc ? <ListGroup horizontal={props.horizontal}>
        <ListGroup.Item variant={"primary"}>ID : {props.targetPostDoc.idActivity}</ListGroup.Item>
        <ListGroup.Item>Nom Post Doc : {props.targetPostDoc.postDoc.namePostDoc}</ListGroup.Item>
        <ListGroup.Item>Nom Superviseur : {props.targetPostDoc.postDoc.nameSupervisor}</ListGroup.Item>
        <ListGroup.Item>Date d'arrivée : {props.targetPostDoc.postDoc.arrivalDate}</ListGroup.Item>
        <ListGroup.Item>Date de départ : {props.targetPostDoc.postDoc.departureDate}</ListGroup.Item>
        <ListGroup.Item>Durée : {props.targetPostDoc.postDoc.duration}</ListGroup.Item>
        <ListGroup.Item>Nationalité : {props.targetPostDoc.postDoc.nationality}</ListGroup.Item>
        <ListGroup.Item>Laboratoire d'origine : {props.targetPostDoc.postDoc.originalLab}</ListGroup.Item>
        <ListGroup.Item>Financement associé : {props.targetPostDoc.postDoc.associatedFunding}</ListGroup.Item>
        <ListGroup.Item>Réf Publi associée : {props.targetPostDoc.postDoc.associatedPubliRef}</ListGroup.Item>
    </ListGroup> : "Target postDoc is not send as props!"


export default PostDocElement