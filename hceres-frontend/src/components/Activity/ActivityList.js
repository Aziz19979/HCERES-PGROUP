import React from 'react';
import './ActivityList.css'

import {Alert, ButtonGroup, ListGroup} from "react-bootstrap";
import Collapse from 'react-bootstrap/Collapse';
import {BiShow, BiHide} from "react-icons/bi";
import EducationList from "./education/EducationList";
import ResearcherElement from "../Researcher/ResearcherElement";
import SrAwardList from "./sraward/SrAwardList";
import PlatformList from "./platform/PlatformList";
import OralComPosterList from "./oral-com-poster/OralComPosterList";
import Button from "react-bootstrap/Button";
import IndustrialContractList from "./industrial-contract/IndustrialContractList";
import InternationalCollaborationList from "./international-collaboration/InternationalCollaborationList";
import ScientificExpertiseList from "./scientific-expertise/ScientificExpertiseList";
import SeiClinicalTrialList from "./sei-clinical-trial/SeiClinicalTrialList";
import IncomingMobilityList from "./incoming-mobility/IncomingMobilityList";
import EditorialActivityList from "./editorial-activity/EditorialActivityList";
import ReviewArticleList from "./review-article/ReviewArticleList";
import PostDocList from "./post-doc/PostDocList";
import OutgoingMobilityList from "./outgoing-mobility/OutgoingMobilityList";
import CompanyCreationList from "./company-creation/CompanyCreationList";
import PatentList from "./patent/PatentList";
import ContractList from "../Other/contract/ContractList";


// if target researcher is set in props will show only related information of target researcher
// otherwise it show actvities by category
export default function ActivityList(props) {
    const targetResearcher = props.targetResearcher;
    const showListByDefault = props.showListByDefault;
    const [successActivityAlert, setSuccessActivityAlert] = React.useState('');
    const [errorActivityAlert, setErrorActivityAlert] = React.useState('');

    const [showEducationList, setShowEducationList] = React.useState(showListByDefault);
    const [showPrixList, setShowPrixList] = React.useState(showListByDefault);
    const [showPlatformList, setShowPlatformList] = React.useState(showListByDefault);
    const [showOralComPosterList, setShowOralComPosterList] = React.useState(showListByDefault);
    const [showSeiIndustrialRDContractList, setShowSeiIndustrialRDContractList] = React.useState(showListByDefault);
    const [showInterCollaborationList, setShowInterCollaborationList] = React.useState(showListByDefault);
    const [showScientificExpertiseList, setShowScientificExpertiseList] = React.useState(showListByDefault);
    const [showEssaiCliniqueList, setShowEssaiCliniqueList] = React.useState(showListByDefault);
    const [showIncomingMobilityList, setShowIncomingMobilityList] = React.useState(showListByDefault);
    const [showOutgoingMobilityList, setShowOutgoingMobilityList] = React.useState(showListByDefault);
    const [showEditorialList, setShowEditorialList] = React.useState(showListByDefault);
    const [showComparnyCreationList, setShowComparnyCreationList] = React.useState(showListByDefault);
    const [showPostDoctoratList, setShowPostDoctoratList] = React.useState(showListByDefault);
    const [showPatentList, setShowPatentList] = React.useState(showListByDefault);
    const [showReviewList, setShowReviewList] = React.useState(showListByDefault);


    // Other list
    const [showContractList, setShowContractList] = React.useState(showListByDefault);

    const setShowAllList = (isShow) => {
        setShowEducationList(isShow)
        setShowPrixList(isShow)
        setShowPlatformList(isShow)
        setShowOralComPosterList(isShow)
        setShowSeiIndustrialRDContractList(isShow)
        setShowInterCollaborationList(isShow)
        setShowScientificExpertiseList(isShow)
        setShowEssaiCliniqueList(isShow)
        setShowIncomingMobilityList(isShow)
        setShowOutgoingMobilityList(isShow)
        setShowEditorialList(isShow)
        setShowComparnyCreationList(isShow)
        setShowPostDoctoratList(isShow)
        setShowPatentList(isShow)
        setShowReviewList(isShow)

        // Other list
        setShowContractList(isShow)
    }

    const showAllList = () => setShowAllList(true)
    const hideAllList = () => setShowAllList(false)
    const activeItemClass = "border-primary text-primary flex-fill"
    const inactiveItemClass = "flex-fill"
    return (

        <div>
            <div>
                <div className={"card"}>
                    <div className={"card-header"}>
                        <h1>
                            Activités
                        </h1>
                    </div>
                    <div className={"card-body"}>
                        <div className={"list_container"} role={"button"}>
                            <ListGroup horizontal={true}>
                                <ButtonGroup>
                                    <Button onClick={showAllList} variant={"outline-primary"}
                                            id={"showAllActivityListBtn"}>Voir tout</Button>
                                    <Button onClick={hideAllList} variant={"outline-secondary"}>Cacher tout</Button>
                                </ButtonGroup>
                            </ListGroup>
                            {targetResearcher && <ResearcherElement targetResearcher={targetResearcher} horizontal/>}
                            <ListGroup horizontal={true}>
                                <ListGroup.Item onClick={() => setShowEducationList(!showEducationList)}
                                                className={showEducationList ? activeItemClass : inactiveItemClass}>
                                    {showEducationList ? <BiShow/> : <BiHide/>}
                                    &nbsp;
                                    Education
                                </ListGroup.Item>

                                <ListGroup.Item onClick={() => setShowPrixList(!showPrixList)}
                                                className={showPrixList ? activeItemClass : inactiveItemClass}>
                                    {showPrixList ? <BiShow/> : <BiHide/>}
                                    &nbsp;
                                Prix
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowPlatformList(!showPlatformList)}
                                            className={showPlatformList ? activeItemClass : inactiveItemClass}>
                                {showPlatformList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Platform
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowOralComPosterList(!showOralComPosterList)}
                                            className={showOralComPosterList ? activeItemClass : inactiveItemClass}>
                                {showOralComPosterList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Communication orale Poster
                            </ListGroup.Item>

                            <ListGroup.Item
                                onClick={() => setShowSeiIndustrialRDContractList(!showSeiIndustrialRDContractList)}
                                className={showSeiIndustrialRDContractList ? activeItemClass : inactiveItemClass}>
                                {showSeiIndustrialRDContractList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Signature d'une contrat industrielle
                            </ListGroup.Item>
                        </ListGroup>
                        <ListGroup horizontal={true}>
                            <ListGroup.Item onClick={() => setShowInterCollaborationList(!showInterCollaborationList)}
                                            className={showInterCollaborationList ? activeItemClass : inactiveItemClass}>
                                {showInterCollaborationList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Collaboration internationale
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowScientificExpertiseList(!showScientificExpertiseList)}
                                            className={showScientificExpertiseList ? activeItemClass : inactiveItemClass}>
                                {showScientificExpertiseList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Expertise scientifique
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowEssaiCliniqueList(!showEssaiCliniqueList)}
                                            className={showEssaiCliniqueList ? activeItemClass : inactiveItemClass}>
                                {showEssaiCliniqueList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Essai clinique
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowIncomingMobilityList(!showIncomingMobilityList)}
                                            className={showIncomingMobilityList ? activeItemClass : inactiveItemClass}>
                                {showIncomingMobilityList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Mobilité entrante
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowOutgoingMobilityList(!showOutgoingMobilityList)}
                                            className={showOutgoingMobilityList ? activeItemClass : inactiveItemClass}>
                                {showOutgoingMobilityList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Mobilité sortante
                            </ListGroup.Item>
                        </ListGroup>
                        <ListGroup horizontal={true}>
                            <ListGroup.Item onClick={() => setShowEditorialList(!showEditorialList)}
                                            className={showEditorialList ? activeItemClass : inactiveItemClass}>
                                {showEditorialList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Edition
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowComparnyCreationList(!showComparnyCreationList)}
                                            className={showComparnyCreationList ? activeItemClass : inactiveItemClass}>
                                {showComparnyCreationList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Création d'entreprise
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowPostDoctoratList(!showPostDoctoratList)}
                                            className={showPostDoctoratList ? activeItemClass : inactiveItemClass}>
                                {showPostDoctoratList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                PostDoctorat
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowPatentList(!showPatentList)}
                                            className={showPatentList ? activeItemClass : inactiveItemClass}>
                                {showPatentList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Brevet
                            </ListGroup.Item>

                            <ListGroup.Item onClick={() => setShowReviewList(!showReviewList)}
                                            className={showReviewList ? activeItemClass : inactiveItemClass}>
                                {showReviewList ? <BiShow/> : <BiHide/>}
                                &nbsp;
                                Revue
                            </ListGroup.Item>
                        </ListGroup>
                    </div>

                    <div>
                        {successActivityAlert && <Alert variant={"success"}
                                                        onClose={() => setSuccessActivityAlert("")}
                                                        dismissible={true}>{successActivityAlert}</Alert>}
                        {errorActivityAlert && <Alert variant={"danger"}
                                                      onClose={() => setErrorActivityAlert("")}
                                                      dismissible={true}>{errorActivityAlert}</Alert>}
                        <Collapse in={showEducationList}>
                            <div>
                                {showEducationList && <EducationList targetResearcher={targetResearcher}/>}
                            </div>
                        </Collapse>

                        <Collapse in={showPrixList}>
                            <div>{showPrixList && <SrAwardList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showPlatformList}>
                            <div>{showPlatformList && <PlatformList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showOralComPosterList}>
                            <div>{showOralComPosterList &&
                                <OralComPosterList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showSeiIndustrialRDContractList}>
                            <div>{showSeiIndustrialRDContractList &&
                                <IndustrialContractList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showInterCollaborationList}>
                            <div>{showInterCollaborationList &&
                                <InternationalCollaborationList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showScientificExpertiseList}>
                            <div>{showScientificExpertiseList &&
                                <ScientificExpertiseList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showEssaiCliniqueList}>
                            <div>{showEssaiCliniqueList &&
                                <SeiClinicalTrialList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showIncomingMobilityList}>
                            <div>{showIncomingMobilityList &&
                                <IncomingMobilityList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showOutgoingMobilityList}>
                            <div>{showOutgoingMobilityList &&
                                <OutgoingMobilityList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showEditorialList}>
                            <div>{showEditorialList && <EditorialActivityList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showComparnyCreationList}>
                            <div>{showComparnyCreationList &&
                                <CompanyCreationList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showPostDoctoratList}>
                            <div>{showPostDoctoratList && <PostDocList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showPatentList}>
                            <div>{showPatentList && <PatentList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>

                        <Collapse in={showReviewList}>
                            <div>{showReviewList && <ReviewArticleList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>
                    </div>
                    </div>
                </div>

                <div className="card">
                    <div className="card-header">
                        <h3 className="card-title">Autres</h3>
                    </div>
                    <div className="card-body">
                        <div className={"list_container"} role={"button"}>
                            <ListGroup horizontal={true}>
                                <ListGroup.Item onClick={() => setShowContractList(!showContractList)}
                                                className={showContractList ? activeItemClass : inactiveItemClass}>
                                    {showContractList ? <BiShow/> : <BiHide/>}
                                    &nbsp;
                                    Contrat
                                </ListGroup.Item>
                            </ListGroup>
                        </div>
                        <Collapse in={showContractList}>
                            <div>{showContractList &&
                                <ContractList targetResearcher={targetResearcher}/>}</div>
                        </Collapse>
                    </div>
                </div>
            </div>
        </div>)
}

