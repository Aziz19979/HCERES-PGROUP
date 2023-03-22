import {ListGroup} from "react-bootstrap";

const IndustrialContractElement = (props) =>
    props.targetIndustrialContract && props.targetIndustrialContract.seiIndustrialRDContract ? <ListGroup horizontal={props.horizontal}>
        <ListGroup.Item variant={"primary"}>ID : {props.targetIndustrialContract.idActivity}</ListGroup.Item>
        <ListGroup.Item>Titre de projet : {props.targetIndustrialContract.seiIndustrialRDContract.projectTitle}</ListGroup.Item>
        <ListGroup.Item>Nom de l'entreprise : {props.targetIndustrialContract.seiIndustrialRDContract.nameCompanyInvolved}</ListGroup.Item>
        <ListGroup.Item>Montant du contract : {props.targetIndustrialContract.seiIndustrialRDContract.agreementAmount}</ListGroup.Item>
        <ListGroup.Item>Publication associé ref: {props.targetIndustrialContract.seiIndustrialRDContract.associatedPubliRef}</ListGroup.Item>
        <ListGroup.Item>Date de depart : {props.targetIndustrialContract.seiIndustrialRDContract.startDate}</ListGroup.Item>
        <ListGroup.Item>Date de fin : {props.targetIndustrialContract.seiIndustrialRDContract.endDate}</ListGroup.Item>
    </ListGroup> : "Target industrialContract is not send as props!"


export default IndustrialContractElement