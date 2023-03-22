const [reactVariableState, setReactVariableState] = React.useState("");

let data = {
    reactVariableState: reactVariableState,
};

<label className='label'>
    templateLabel
</label>
<input
    type={"text"}
    placeholder="templateLabel"
    className="input-container"
    value={reactVariableState}
    onChange={e => setReactVariableState(e.target.value)}
    required/>