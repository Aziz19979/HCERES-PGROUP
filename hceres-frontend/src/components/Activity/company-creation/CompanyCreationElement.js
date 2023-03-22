import {ListGroup} from "react-bootstrap";

const CompanyCreationElement = (props) =>
    props.targetCompanyCreation && props.targetCompanyCreation.companyCreation ? <ListGroup horizontal={props.horizontal}>
        <ListGroup.Item variant={"primary"}>ID : {props.targetCompanyCreation.idActivity}</ListGroup.Item>
        <ListGroup.Item>Nom de l'entreprise : {props.targetCompanyCreation.companyCreation.companyCreationName}</ListGroup.Item>
        <ListGroup.Item>Date de création : {props.targetCompanyCreation.companyCreation.companyCreationDate}</ListGroup.Item>
        <ListGroup.Item>Entreprise Active? : {props.targetCompanyCreation.companyCreation.companyCreationActive?'True':'False'}</ListGroup.Item>
    </ListGroup> : "Target companyCreation is not send as props!"


export default CompanyCreationElement