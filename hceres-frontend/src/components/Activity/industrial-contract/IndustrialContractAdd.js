import React from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import ResearcherSelect from "../../util/ResearcherSelect";
import LoadingIcon from "../../util/LoadingIcon";
import {addIndustrialContract} from "../../../services/Activity/industrial-contract/IndustrialContractActions";

// If targetResearcher is set in props use it as default without charging list from database
// else load list de chercheurs from database
function IndustrialContractAdd(props) {
    // parameter constant (Add Template)
    const targetResearcher = props.targetResearcher;
    const onHideParentAction = props.onHideAction

    // Cached state (Add Template)

    // UI states (Add Template)
    const [showModal, setShowModal] = React.useState(true);
    const [isLoading, setIsLoading] = React.useState(false);


    // Form state (Add Template)
    const [researcherId, setResearcherId] = React.useState(targetResearcher ? targetResearcher.researcherId : "");
    const [StartDate, setStartDate] = React.useState("");
    const [NameCompanyInvolved, setNameCompanyInvolved] = React.useState("");
    const [ProjectTitle, setProjectTitle] = React.useState("");
    const [AgreementAmount, setAgreementAmount] = React.useState("");
    const [EndDate, setEndDate] = React.useState("");
    const [AssociatedPubliRef, setAssociatedPubliRef] = React.useState("");


    const handleClose = (msg = null) => {
        setShowModal(false);
        onHideParentAction(msg);
    };


    const handleSubmit = (event) => {
        event.preventDefault();
        setIsLoading(true);
        let data = {
            researcherId: researcherId,
            AssociatedPubliRef: AssociatedPubliRef,
            EndDate: EndDate,
            AgreementAmount: AgreementAmount,
            ProjectTitle: ProjectTitle,
            NameCompanyInvolved: NameCompanyInvolved,
            StartDate: StartDate
        };

        addIndustrialContract(data).then(response => {
            // const activityId = response.data.researcherId;
            const msg = {
                "successMsg": "IndustrialContract ajouté avec un id " + response.data.idActivity,
            }
            handleClose(msg);
        })
            .finally(() => setIsLoading(false)).catch(error => {
            console.log(error);
            const msg = {
                "errorMsg": "Erreur IndustrialContract non ajouté, response status: " + error.response.status,
            }
            handleClose(msg);
        })
            .finally(() => setIsLoading(false))
    }

    return (
        <div>
            <Modal show={showModal} onHide={handleClose}>
                <form onSubmit={handleSubmit}>
                    <Modal.Header closeButton>
                        <Modal.Title>Contrat industrielle</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>


                        <label className='label'>
                            Chercheur
                        </label>
                        <ResearcherSelect
                            targetResearcher={targetResearcher}
                            onchange={React.useCallback(resId => setResearcherId(resId),[])}
                        />

                        <label className='label'>
                            Date de début
                        </label>
                        <input
                            type="date"
                            className='input-container'
                            onChange={e => setStartDate(e.target.value)}
                            required/>

                        <label className='label'>
                            Nom Entreprise impliquée
                        </label>
                        <input
                            placeholder='Description'
                            className='input-container'
                            name="Nom Entreprise impliquée"
                            type="Nom Entreprise impliquée"
                            value={NameCompanyInvolved}
                            onChange={e => setNameCompanyInvolved(e.target.value)}
                            required/>


                        <label className='label'>
                            Titre du projet
                        </label>
                        <input
                            placeholder='ProjectTitle '
                            className='input-container'
                            name="ProjectTitle"
                            type="ProjectTitle"
                            value={ProjectTitle}
                            onChange={e => setProjectTitle(e.target.value)}
                            required/>

                        <label className='label'>
                            Montant de l'accord
                        </label>
                        <input
                            type="number"
                            placeholder='Affiliation'
                            className='input-container'
                            name="AgreementAmount"
                            value={AgreementAmount}
                            onChange={e => setAgreementAmount(e.target.value)}
                            required/>

                        <label className='label'>
                            Date de fin
                        </label>
                        <input
                            type="date"
                            className='input-container'
                            onChange={e => setEndDate(e.target.value)}
                            required/>

                        <label className='label'>
                            Référence de la publication associée
                        </label>
                        <input
                            placeholder='ProjectTitle '
                            className='input-container'
                            name="AssociatedPubliRef"
                            type="AssociatedPubliRef"
                            value={AssociatedPubliRef}
                            onChange={e => setAssociatedPubliRef(e.target.value)}
                            required/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleClose}>
                            Close
                        </Button>
                        <Button variant="outline-primary" type={"submit"} disabled={isLoading}>
                            {isLoading ? <LoadingIcon/> : null}
                            {isLoading ? 'Ajout en cours...' : 'Ajouter'}
                        </Button>

                    </Modal.Footer>
                </form>
            </Modal>
        </div>
    );
}

export default IndustrialContractAdd;
