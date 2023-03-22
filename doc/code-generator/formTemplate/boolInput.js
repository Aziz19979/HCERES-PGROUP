<label className='label'>
    templateLabel
</label>
<input
    name="reactVariableState"
    type="checkbox"
    className="input-container"
    checked={reactVariableState}
    onChange={e => setReactVariableState(e.target.checked)}
    />