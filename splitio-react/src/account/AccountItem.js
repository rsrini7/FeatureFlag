export function AccountItem(props) {
    const accountNo = props.accountNo;
    const name = props.name;
    const bal = props.bal;
    const country = props.country;

    return (
        <tr>
            <td>{accountNo}</td>
            <td>{name}</td>
            <td>{bal}</td>
            <td>{country}</td>
        </tr>
    )
}