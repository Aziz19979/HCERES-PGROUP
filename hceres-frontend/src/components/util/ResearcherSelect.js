import React from "react";
import Select from "react-select";
import FixRequiredSelect from "./FixRequiredSelect";
import {researcherToString} from "./ResearcherToString";
import {ListGroup} from "react-bootstrap";
import {fetchListResearchers} from "../../services/Researcher/ResearcherActions";

/**
 * Return a <select> html element for all researchers in application
 *
 * @param props: {
 *     onChange: callback function with id of selected researcher as parameter,
 *               or an array of ids if isMulti is enabled
 *     isMulti: specify if select is multi select, otherwise it is single
 *     targetResearcher: used for default value if present
 * }
 * @returns {JSX.Element} {Select} element, or {ListGroup.Item} if not selection is needed
 * @constructor
 */
function ResearcherSelect(props) {
    // parameter constant
    const onChangeResearcherId = props.onchange;
    const isMulti = props.isMulti ? props.isMulti : false;
    const placeHolder = isMulti ? "Choisie les chercheurs..." : "Choisie un chercheur...";

    const targetResearcher = props.targetResearcher;
    // see https://www.freecodecamp.org/news/what-every-react-developer-should-know-about-state/
    const defaultValue = React.useMemo(() => {
        let value = targetResearcher ? {
            value: targetResearcher.researcherId,
            label: researcherToString(targetResearcher),
            isFixed: true,
        } : undefined;
        if (value) {
            // if default value is set, call on change value so form variable is affected
            // even when user do not change input
            if (isMulti) {
                onChangeResearcherId([value.value]);
            } else {
                onChangeResearcherId(value.value);
            }
        }
        return value;
    }, [targetResearcher, onChangeResearcherId, isMulti]);

    // Cached state (for selection options)
    const [researchers, setResearchers] = React.useState([]);
    const doRequireUsingSelectList = isMulti || !targetResearcher
    const researcherSelectOptions = researchers.map(res => ({
        value: res.researcherId,
        label: researcherToString(res),
    }))

    // select value state
    const [value, setValue] = React.useState(defaultValue);

    React.useEffect(() => {
        if (doRequireUsingSelectList) {
            fetchListResearchers().then(list => setResearchers(list));
        }
    }, [doRequireUsingSelectList])


    if (!doRequireUsingSelectList) {
        // there is no multi select option and target researcher is set
        // select is not required, returning list group instead
        return <ListGroup.Item
            variant={"primary"}>{researcherToString(props.targetResearcher)}</ListGroup.Item>
    } else {

        // selection is required
        const onChangeSelection = (e, option) => {
            let newValue = e;
            if (option)
                switch (option.action) {
                    case "remove-value":
                    case "pop-value":
                        if (option.removedValue.isFixed)
                            return;
                        break;
                    case "clear":
                        newValue = option.removedValues.filter(item => item.isFixed)
                        break;
                    default:
                        break;
                }
            setValue(newValue)
            if (newValue === null) {
                if (isMulti) {
                    onChangeResearcherId([]);
                } else {
                    onChangeResearcherId("");
                }
            } else {
                if (isMulti) {
                    onChangeResearcherId(newValue.map(opt => opt.value));
                } else {
                    onChangeResearcherId(newValue.value)
                }
            }
        }

        return <FixRequiredSelect
            SelectComponent={Select}
            options={researcherSelectOptions}
            value={value}
            placeholder={placeHolder}
            onChange={onChangeSelection}
            isMulti={isMulti}
            required={true}
        />
    }

}

export default ResearcherSelect;