async function handler() {
    try {
        await validateParams()
        const dbResults = await dbQuery()
        const serviceResults = await serviceCall()
        return {dbResults, serviceResults}
    } catch (error) {
        console.log(error)
    }
}