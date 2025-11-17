import UsersList from "../pages/admin/UsersList";
import ElevesList from "../pages/admin/ElevesList";
import ClassesList from "../pages/admin/ClassesList";
import MatieresList from "../pages/admin/MatieresList";
import AdminStats from "../pages/admin/AdminStats";
import Inscriptions from "../pages/admin/Inscriptions";

export default function List( {category} ) {
    switch(category) {
        case "Utilisateurs":
            return <UsersList/>
        case "Eleves":
            return <ElevesList/>
        case "Matieres":
            return <MatieresList/>
        case "Classes":
            return <ClassesList/>            
        case "Inscriptions":
            return <Inscriptions/>
        default:
            return <UsersList/>;
    }
}