import React from 'react';
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import ResearcherSelect from "../../util/ResearcherSelect";
import LoadingIcon from "../../util/LoadingIcon";
import {addCompanyCreation} from "../../../services/Activity/company-creation/CompanyCreationActions";

// If targetResearcher is set in props use it as default without charging list from database
// else load list de chercheurs from database
function CompanyCreationAdd(props) {
    // parameter constant (Add Template)
    const targetResearcher = props.targetResearcher;
    const onHideParentAction = props.onHideAction

    // Cached state (Add Template)

    // UI states (Add Template)
    const [showModal, setShowModal] = React.useState(true);
    const [isLoading, setIsLoading] = React.useState(false);


    // Form state (Add Template)
    const [formData, setFormData] = React.useState({
        researcherId: targetResearcher ? targetResearcher.researcherId : "",
        companyCreationName: "",
        companyCreationDate: "",
        companyCreationActive: false,
    });

    const handleClose = (msg = null) => {
        setShowModal(false);
        onHideParentAction(msg);
    };


    const handleSubmit = (event) => {
        event.preventDefault();
        setIsLoading(true);

        addCompanyCreation(formData).then(response => {
            // const activityId = response.data.researcherId;
            const msg = {
                "successMsg": "CompanyCreation ajouté avec un id " + response.data.idActivity,
            }
            handleClose(msg);
        })
            .finally(() => setIsLoading(false)).catch(error => {
            console.log(error);
            const msg = {
                "errorMsg": "Erreur CompanyCreation non ajouté, response status: " + error.response.status,
            }
            handleClose(msg);
        })
            .finally(() => setIsLoading(false))
    }

    const handleFormChange = (event) => {
        const {name, value, type, checked} = event.target
        setFormData(prevFormData => ({
            ...prevFormData,
            [name]: type === "checkbox" ? checked: value
        }))
    }

    return (
        <div>
            <Modal show={showModal} onHide={handleClose}>
                <form onSubmit={handleSubmit}>
                    <Modal.Header closeButton>
                        <Modal.Title>CompanyCreation</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>


                        <label className='label'>
                            Chercheur
                        </label>
                        <ResearcherSelect
                            targetResearcher={targetResearcher}
                            onchange={React.useCallback(resId => setFormData(prevFormData => ({
                                ...prevFormData,
                                researcherId: resId
                            })), [])}
                        />
                        <label className='label'>
                            Nom de l'entreprise
                        </label>
                        <input
                            name="companyCreationName"
                            type={"text"}
                            placeholder="Nom de l'entreprise"
                            className="input-container"
                            value={formData.companyCreationName}
                            onChange={handleFormChange}
                            required/>

                        <label className='label'>
                            Date de création
                        </label>
                        <input
                            name="companyCreationDate"
                            type="date"
                            className='input-container'
                            value={formData.companyCreationDate}
                            onChange={handleFormChange}
                            required/>

                        <label className='label'>
                            Entreprise Active?
                        </label>
                        <input
                            name="companyCreationActive"
                            type="checkbox"
                            className="input-container"
                            checked={formData.companyCreationActive}
                            onChange={handleFormChange}
                        />

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

export default CompanyCreationAdd;
