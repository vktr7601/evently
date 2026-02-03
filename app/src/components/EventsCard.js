const EventsCard = (props) => {
    return (
        <article>
            <div> {props.name}</div>
            <div> {props.bio}</div>
            <div> {props.place}</div>
        </article>
    )
}

export default EventsCard;